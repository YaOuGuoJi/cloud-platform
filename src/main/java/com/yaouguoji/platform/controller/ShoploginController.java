package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ShopAccountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopAccountService;
import com.yaouguoji.platform.service.SmsClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ShoploginController {

    @Resource
    private ShopAccountService shopAccountService;
    @Resource
    private SmsClientService smsClientService;

    @RequestMapping("/shop/gitShopVerifyCode")
    public CommonResult gitShopVerifyCode(String phoneNum){
        if (StringUtils.isEmpty(phoneNum)){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int num = smsClientService.sendShopVerifyCode(phoneNum);
        if (num < 0){
           return CommonResult.fail(403,"获取验证码失败");
        }
        return CommonResult.success("获取验证码成功");
    }

    @RequestMapping("/shop/login")
    public CommonResult login(String code, String phoneNum){
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phoneNum)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int num = smsClientService.verifyCode(code,phoneNum);
        if (num < 0){
            return CommonResult.fail(403,"输入验证码验证失败");
        }
        ShopAccountDTO shopAccountDTO = shopAccountService.findShopAccountByShopId(phoneNum);
        if (shopAccountDTO == null){
            return CommonResult.fail(403,"不存在该商户");
        }
        shopAccountService.addShopAccount(shopAccountDTO);
        return CommonResult.success("登录成功");
    }

}
