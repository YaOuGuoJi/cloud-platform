package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserFrequencyCountAndPriceCountDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFrquencyCountAndPriceCountServiceTest {
    @Resource
    private UserFrequencyCountAndPriceCountService userFrequencyCountAndPriceCountService;

    @Test
    public void selectUserFrequencyCountAndPriceCount() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        UserFrequencyCountAndPriceCountDTO userFrequencyCountAndPriceCountDTO = userFrequencyCountAndPriceCountService.selectUserFrequencyCountAndPriceCount(100001, startTime, endTime);
        Assert.assertNotNull(userFrequencyCountAndPriceCountDTO);
    }
}
