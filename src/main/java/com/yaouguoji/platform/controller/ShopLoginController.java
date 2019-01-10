package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.ShopInfoDTO;
import com.xianbester.api.service.ShopInfoService;
import com.xianbester.api.service.SmsClientService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.constant.TokenParameters;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author yanrui
 */
@RestController
public class ShopLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopLoginController.class);

    @Reference
    private ShopInfoService shopInfoService;
    @Reference
    private SmsClientService smsClientService;

    @GetMapping("/shop/isLogin")
    public CommonResult isLogin(HttpServletRequest request) {
        String token = TokenUtil.getShopToken(request);
        try {
            TokenUtil.verifyToken(token);
        } catch (Exception e) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    @GetMapping("/shop/verifyCode")
    public CommonResult shopVerifyCode(String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByPhone(phoneNum);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND.value, "该手机号未注册为商户");
        }
        String code = smsClientService.sendShopVerifyCode(phoneNum);
        if (StringUtils.isBlank(code) || code.length() != 6) {
            return CommonResult.fail(HttpStatus.ERROR.value, "获取验证码失败，请稍后再试");
        }
        return CommonResult.success(code);
    }

    @PostMapping("/shop/login")
    public CommonResult login(String code, String phoneNum, HttpServletResponse response) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phoneNum)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByPhone(phoneNum);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND.value, "该手机号未注册为商户");
        }
        int num = smsClientService.verifyShopCode(code, phoneNum);
        if (num < 0) {
            return CommonResult.fail(403, "验证失败");
        }
        try {
            Map<String, String> data = Maps.newHashMap();
            data.put("shopId", shopInfoDTO.getShopId() + "");
            TokenUtil.updateShopToken2Cookie(response, data);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("token加密失败!");
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("登录成功");
    }

    /**
     * 商户登出
     *
     * @param response
     * @return
     */
    @PostMapping("/shop/logout")
    public CommonResult logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenParameters.SHOP_COOKIE_NAME, "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return CommonResult.success();
    }

}
