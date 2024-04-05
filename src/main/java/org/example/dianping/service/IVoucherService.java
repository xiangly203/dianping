package org.example.dianping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.Voucher;


public interface IVoucherService extends IService<Voucher> {

    Result queryVoucherOfShop(Long shopId);

    void addSeckillVoucher(Voucher voucher);
}
