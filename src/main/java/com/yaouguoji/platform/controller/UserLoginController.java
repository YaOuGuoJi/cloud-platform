package com.yaouguoji.platform.controller;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.constant.TokenParameters;
import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.SmsClientService;
import com.yaouguoji.platform.service.UserInfoService;
import com.yaouguoji.platform.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhangqiang
 * @date 2018-12-11
 */
@RestController
public class UserLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private SmsClientService smsClientService;

    /**
     * 发送验证码
     *
     * @param phoneNum
     * @return
     */
    @GetMapping("/user/verifyCode")
    public CommonResult userVerifyCode(String phoneNum) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (StringUtils.isBlank(phoneNum) || !Pattern.matches(regex, phoneNum)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO userInfo = userInfoService.findUserInfoByPhoneNum(phoneNum);
        if (userInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND.value, "该用户未注册");
        }
        String  result = smsClientService.sendVerifyCode(phoneNum);
        if (StringUtils.isBlank(regex) || result.length() != 6) {
            return CommonResult.fail(HttpStatus.ERROR.value, "获取验证码失败，请稍后再试！");
        }
        return CommonResult.success(result);
    }

    /**
     * 用户登录
     *
     * @param phoneNum 手机号
     * @param code     验证码
     * @param response
     * @return
     */
    @PostMapping("/user/login")
    public CommonResult userLogin(String phoneNum, String code, HttpServletResponse response) {
        String phoneRegex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        String codeRegex = "[0-9]{6}";
        if (StringUtils.isBlank(phoneNum) || !Pattern.matches(phoneRegex, phoneNum) || StringUtils.isBlank(code) || !Pattern.matches(codeRegex, code)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO userInfo = userInfoService.findUserInfoByPhoneNum(phoneNum);
        if (userInfo == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int result = smsClientService.verifyCode(phoneNum, code);
        if (result < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        Map<String, String> data = Maps.newHashMap();
        data.put("userId", userInfo.getUserId() + "");
        try {
            TokenUtil.updateUserToken2Cookie(response, data);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("token加密失败!");
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("登录成功");
    }

    /**
     * 判断用户是否登录
     *
     * @param request
     * @return
     */
    @GetMapping("/user/isLogin")
    public CommonResult isLogin(HttpServletRequest request) {
        String token = TokenUtil.getUserToken(request);
        try {
            TokenUtil.verifyToken(token);
        } catch (Exception e) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 用户登出
     *
     * @param response
     * @return
     */
    @PostMapping("/user/logout")
    public CommonResult logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenParameters.USER_COOKIE_NAME, "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return CommonResult.success();
    }

}
