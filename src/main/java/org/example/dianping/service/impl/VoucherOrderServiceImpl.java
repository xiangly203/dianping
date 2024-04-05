package org.example.dianping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Nonnull;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.bouncycastle.asn1.cms.Time;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.VoucherMsg;
import org.example.dianping.entity.VoucherOrder;
import org.example.dianping.mapper.VoucherOrderMapper;
import org.example.dianping.service.ISeckillVoucherService;
import org.example.dianping.service.IVoucherOrderService;
import org.example.dianping.utils.RedisIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.dianping.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.*;

import static org.example.dianping.utils.RedisConstants.SECKILL_STOCK_KEY;


@Service
@Slf4j
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {
    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    @Lazy
    private IVoucherOrderService voucherOrderService;

    @Resource
    private PulsarTemplate<Object> pulsarTemplate;
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private IVoucherOrderService proxy;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }


    private void handleVoucherOrder(VoucherOrder voucherOrder) throws PulsarClientException {
        Long userId = voucherOrder.getUserId();
        RLock lock = redissonClient.getLock("lock:order:" + userId);
        boolean isLock = lock.tryLock();
        if (!isLock) {
            log.error("不允许重复下单");
            return;
        }
        try {
            voucherOrderService.createVoucherOrder(voucherOrder);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Result seckillVoucher(Long voucherId) {
        Long userId = UserHolder.getUser().getId();
        String res = stringRedisTemplate.opsForValue().get(SECKILL_STOCK_KEY + voucherId);
        if (res == null) {
            return Result.fail("优惠券不存在");
        }
        long orderId = redisIdWorker.nextId("order");
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                voucherId.toString(), userId.toString(), String.valueOf(orderId)
        );
        assert result != null;
        int r = result.intValue();
        if (r != 0) {
            return Result.fail(r == 1 ? "库存不足" : "不能重复下单");
        }
        CreateOrderWithMessage(new VoucherMsg(userId, orderId, voucherId, LocalDateTime.now()));
        return Result.ok(orderId);
    }

    public void CreateOrderWithMessage(VoucherMsg voucherMsg) {
        var future = executorService.submit(() -> {
            try {
                pulsarTemplate.newMessage(voucherMsg).
                        withTopic("seckill-voucher")
                        .send();
                log.debug("秒杀成功，{}", voucherMsg.toString());
            } catch (PulsarClientException e) {
                log.error(e.toString(), e);
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Nonnull
    @Transactional(rollbackFor = Exception.class)
    public Result getResult(Long voucherId) {
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createVoucherOrder(VoucherOrder voucherOrder) {
        Long userId = voucherOrder.getUserId();
        int count = Math.toIntExact(query().eq("user_id", userId)
                .eq("voucher_id", voucherOrder.getVoucherId())
                .count());
        if (count > 0) {
            log.error("用户已经购买过一次");
            return;
        }
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherOrder.getVoucherId())
                .gt("stock", 0)
                .update();
        if (!success) {
            log.error("库存不足！");
            return;
        }
        save(voucherOrder);
    }

    @PulsarListener(
            subscriptionName = "user-topic-subscription",
            topics = "seckill-voucher",
            schemaType = SchemaType.JSON
    )
    public void userTopicListener(VoucherMsg v) {
        log.info("消费{}{}", "seckill-voucher", v.toString());
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setId(v.getOrderId());
        voucherOrder.setUserId(v.getUserId());
        voucherOrder.setVoucherId(v.getVoucherId());
        voucherOrder.setStatus(0);
        voucherOrder.setCreateTime(v.getCreateTime());
        executorService.submit(() -> {
            try {
                handleVoucherOrder(voucherOrder);
            } catch (Exception e) {
                log.error("出现异常，{}", e);
            }
        });
    }
}
