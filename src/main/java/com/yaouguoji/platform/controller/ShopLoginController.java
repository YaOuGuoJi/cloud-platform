package com.yaouguoji.platform.controller;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopInfoService;
import com.yaouguoji.platform.service.SmsClientService;
import com.yaouguoji.platform.util.ShopTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author yanrui
 */
@RestController
public class ShopLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopLoginController.class);

    @Resource
    private ShopInfoService shopInfoService;
    @Resource
    private SmsClientService smsClientService;

    @RequestMapping("/shop/verifyCode")
    public CommonResult shopVerifyCode(String phoneNo) {
        if (StringUtils.isEmpty(phoneNo)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByPhone(phoneNo);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND.value, "该手机号未注册为商户");
        }
        int num = smsClientService.sendShopVerifyCode(phoneNo);
        if (num < 0) {
            return CommonResult.fail(HttpStatus.ERROR.value, "获取验证码失败，请稍后再试");
        }
        return CommonResult.success(num);
    }

    @RequestMapping("/shop/login")
    public CommonResult login(String code, String phoneNo, HttpServletResponse response) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phoneNo)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByPhone(phoneNo);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND.value, "该手机号未注册为商户");
        }
        int num = smsClientService.verifyCode(code, phoneNo);
        if (num < 0) {
            return CommonResult.fail(403, "验证失败");
        }
        try {
            Map<String, String> data = Maps.newHashMap();
            data.put("shopId", shopInfoDTO.getShopId() + "");
            ShopTokenUtil.updateToken2Cookie(response, data);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("token加密失败!");
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("登录成功");
    }

}
