package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopInfoService;
import com.yaouguoji.platform.service.SmsClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ShopLoginController {

    @Resource
    private ShopInfoService shopInfoService;
    @Resource
    private SmsClientService smsClientService;

    @RequestMapping("/gitShopVerifyCode")
    public CommonResult gitShopVerifyCode(String phoneNo) {
        if (StringUtils.isEmpty(phoneNo)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByPhone(phoneNo);
        if (shopInfoDTO == null) {
            return CommonResult.fail(403, "不存在该商户");
        }
        int num = smsClientService.sendShopVerifyCode(phoneNo);
        if (num < 0) {
            return CommonResult.fail(403, "获取验证码失败");
        }
        return CommonResult.success("获取验证码成功");
    }

    @RequestMapping("/login")
    public CommonResult login(String code, String phoneNo) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phoneNo)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int num = smsClientService.verifyCode(code, phoneNo);
        if (num < 0) {
            return CommonResult.fail(403, "输入验证码验证失败");
        }
        return CommonResult.success("登录成功");
    }

}