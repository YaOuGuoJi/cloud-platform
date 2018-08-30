package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.dto.ShopDTO;
import com.yaouguoji.platform.service.ShopInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/shop")
    public CommonResult addShop(@RequestBody ShopDTO shopDTO) {
        if (shopDTO == null || StringUtils.isBlank(shopDTO.getShopName())) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int result = shopInfoService.insertShopInfo(shopDTO);
        return result > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    @PutMapping("/shop")
    public CommonResult updateShop(@RequestBody ShopDTO shopDTO) {
        if (shopDTO == null || StringUtils.isBlank(shopDTO.getShopName())) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int result = shopInfoService.updateShopInfo(shopDTO);
        return result > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    @DeleteMapping("/shop/{id}")
    public CommonResult deleteShop(@PathVariable("id") int id) {
        if (id <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        shopInfoService.deleteShopInfo(id);
        return CommonResult.success();
    }
}
