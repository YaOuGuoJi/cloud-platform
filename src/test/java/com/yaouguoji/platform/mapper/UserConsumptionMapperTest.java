package com.yaouguoji.platform.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConsumptionMapperTest {

    @Resource
    private UserConsumptionMapper userConsumptionMapper;

    @Test
    public void recentYearCSPTest() {
        BigDecimal i = userConsumptionMapper.recentYearCSP(100000001);
        Assert.assertEquals("901129.3", i.toString());
    }
}
