package com.yaouguoji.platform.service;

public interface SmsClientService {

    /**
     * 发送商户验证码
     *
     * @param phoneNum
     * @return
     */
    int sendShopVerifyCode(String phoneNum);

    /**
     * 验证手机验证码
     *
     * @param phoneNum
     * @param code
     * @return
     */
    int verifyCode(String phoneNum, String code);
}
