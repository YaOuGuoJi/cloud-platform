package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserPriceCountDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowUserInfoToShowTest {
    @Resource
    private ShowUserInfoToShop showUserInfoToShop;

    @Test
    public void selectAgeAndSexSplitTest() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        Map<String, Map<String, Integer>> userSexAndAges = showUserInfoToShop.selectAgeAndSexSplit(100001, startTime, endTime);
        Assert.assertNotNull(userSexAndAges);
    }
    @Test
    public void selectUserPriceCount() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        UserPriceCountDTO userPriceCountDTO = showUserInfoToShop.selectUserPriceCount(100001, startTime, endTime);
        Assert.assertNotNull(userPriceCountDTO);
    }
    @Test
    public void selectUserFrequencyCount(){
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        Map<String, Object> userFrequency = showUserInfoToShop.selectUserFrequencyCount(100001, startTime, endTime);
        Assert.assertNotNull(userFrequency);
    }
}
