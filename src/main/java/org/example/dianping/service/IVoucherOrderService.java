package org.example.dianping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.annotation.Nonnull;
import org.apache.pulsar.client.api.PulsarClientException;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.VoucherOrder;
import org.springframework.transaction.annotation.Transactional;


public interface IVoucherOrderService extends IService<VoucherOrder> {

    /**
     * 秒杀优惠券
     *
     * @param voucherId 券id
     * @return {@link Result}
     */
    Result seckillVoucher(Long voucherId) throws PulsarClientException;

    /**
     * 得到结果
     *
     * @param voucherId 券id
     * @return {@link Result}
     */
    Result getResult(Long voucherId);

    /**
     * 创建优惠券订单
     *
     * @param voucherOrder 券订单
     */
    @Nonnull
    @Transactional(rollbackFor = Exception.class)
    void createVoucherOrder(VoucherOrder voucherOrder) throws PulsarClientException;
}
