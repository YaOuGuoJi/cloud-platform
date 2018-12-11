package com.yaouguoji.platform.service;

/**
 * @author liuwen
 */
public interface SmsClientService {

    /**
     * 发送验证码
     *
     * @param phoneNum
     * @return
     */
    int sendVerifyCode(String phoneNum);

    /**
     * 验证手机验证码
     *
     * @param phoneNum
     * @param code
     * @return
     */
    int verifyCode(String phoneNum, String code);
}
