package org.example.dianping.controller;


import org.apache.pulsar.client.api.PulsarClientException;
import org.example.dianping.dto.Result;
import org.example.dianping.service.IVoucherOrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {
    @Resource
    private IVoucherOrderService voucherOrderService;

    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) throws PulsarClientException {
        return voucherOrderService.seckillVoucher(voucherId);
    }
}
