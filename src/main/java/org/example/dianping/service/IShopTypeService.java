package org.example.dianping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.ShopType;

public interface IShopTypeService extends IService<ShopType> {

    /**
     * 获取商品类型列表
     *
     * @return {@link Result}
     */
    Result getTypeList();
}
