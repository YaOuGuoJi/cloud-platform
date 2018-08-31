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
    public CommonResult addShop(String shopName, int brandId, int regionId) {
        if (StringUtils.isBlank(shopName)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopName(shopName);
        shopDTO.setBrandId(brandId > 0 ? brandId : 0);
        shopDTO.setRegionId(regionId > 0 ? regionId : 0);
        int result = shopInfoService.insertShopInfo(shopDTO);
        return result > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    @PutMapping("/shop")
    public CommonResult updateShop(int id, String shopName, int brandId, int regionId) {
        if (id <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(id);
        if (StringUtils.isNotBlank(shopName)) {
            shopDTO.setShopName(shopName);
        }
        if (brandId > 0) {
            shopDTO.setBrandId(brandId);
        }
        if (regionId > 0) {
            shopDTO.setRegionId(regionId);
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
