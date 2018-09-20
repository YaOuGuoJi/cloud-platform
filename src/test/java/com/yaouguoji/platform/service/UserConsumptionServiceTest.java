package com.yaouguoji.platform.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConsumptionServiceTest {
    @Resource
    private UserConsumptionService userConsumptionService;
    @Test
    public void RecentYuearConsumPtionTest(){
        BigDecimal i = userConsumptionService.recentYearConsumption(100000001);
        //Assert.assertEquals("00",i.toString() );
    }
}
