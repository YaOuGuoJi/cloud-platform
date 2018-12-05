package com.yaouguoji.platform.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/12/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsClientServiceTest {

    @Resource
    private SmsClientService smsClientService;

    @Test
    public void testSendVerifyCode() {
        smsClientService.sendShopVerifyCode("15991183772");
        smsClientService.sendShopVerifyCode("18792863414");

    }

    @Test
    public void verify() {
        int i = smsClientService.verifyCode("15991183772", "384320");
        Assert.assertEquals(1, i);
    }
}
