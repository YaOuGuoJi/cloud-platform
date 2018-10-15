package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserFrequencyEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFrequencyTest {
    @Resource
    private UserFrequencyMapper userFrequencyMapper;

    @Test
    public void selectUserFrequency() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        List<UserFrequencyEntity> userFrequencyEntities = userFrequencyMapper.selectUserFrequency(100001, startTime, endTime);
        Assert.assertNotNull(userFrequencyEntities);
    }

    @Test
    public void selectUserTotalAndAverage() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        final Map<String, BigDecimal> userTotalAndAverageFrequency = userFrequencyMapper.selectUserTotalAndAverageFrequency(100001, startTime, endTime);
        Assert.assertNotNull(userTotalAndAverageFrequency);
    }

}
