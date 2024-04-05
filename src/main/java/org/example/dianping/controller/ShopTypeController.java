package org.example.dianping.controller;


import jakarta.annotation.Resource;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.ShopType;
import org.example.dianping.service.IShopTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/shop-type")
public class ShopTypeController {
    @Resource
    private IShopTypeService typeService;

    @GetMapping("list")
    public Result queryTypeList() {
        return typeService.getTypeList();
    }
}
