package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ShopAccountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ShoploginController {

    @Resource
    private ShopAccountService shopAccountService;

    @RequestMapping("/shop/login")
    public CommonResult login(String phoneNum, HttpServletResponse response){
        if (StringUtils.isEmpty(phoneNum)){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        ShopAccountDTO shopAccountDTO = shopAccountService.findShopAccountByShopId(phoneNum);
        if (shopAccountDTO == null){
            return CommonResult.fail(403,"不存在该商户");
        }
        return CommonResult.success();
    }

}
