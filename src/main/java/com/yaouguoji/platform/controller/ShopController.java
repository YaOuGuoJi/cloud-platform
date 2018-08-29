package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.constant.CommonResult;
import com.yaouguoji.platform.constant.HttpStatus;
import com.yaouguoji.platform.dto.ShopDTO;
import com.yaouguoji.platform.service.ShopInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Component
@RestController
public class ShopController {

    @Resource
    private ShopInfoService shopInfoService;

    @GetMapping("/shop/{id}")
    public CommonResult shopInfo(@PathVariable("id") int shopId) {

        ShopDTO shopDTO = shopInfoService.getShopInfoByShopId(shopId);
        if (shopDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(shopDTO);
    }
}
