package org.example.dianping.controller;


import org.example.dianping.dto.Result;
import org.example.dianping.entity.Voucher;
import org.example.dianping.service.IVoucherService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;


@RestController
@RequestMapping("/voucher")
public class VoucherController {
    @Resource
    private IVoucherService voucherService;

    @PostMapping("/normal")
    public Result addVoucher(@RequestBody Voucher voucher) {
        voucherService.save(voucher);
        return Result.ok(voucher.getId());
    }

    @PostMapping("/seckill")
    public Result addSeckillVoucher(@RequestBody Voucher voucher) {
        voucherService.addSeckillVoucher(voucher);
        return Result.ok(voucher.getId());
    }

    @GetMapping("/list/{shopId}")
    public Result queryVoucherOfShop(@PathVariable("shopId") Long shopId) {
        return voucherService.queryVoucherOfShop(shopId);
    }
}
