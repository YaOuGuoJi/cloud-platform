package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserFrequencyCountAndPriceCountEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFrequencyCountAndPriceCountMapperTest {
    @Resource
    private UserFrequencyCountAndPriceCountMapper userFrequencyCountAndPriceCountMapper;

    @Test
    public void selectUserFrequencyCountAndPriceCountEntityTest() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        List<UserFrequencyCountAndPriceCountEntity> userFrequencyCountAndPriceCount = userFrequencyCountAndPriceCountMapper.selectUserFrequencyCountAndPriceCount(100001, startTime, endTime);
        Assert.assertNotNull(userFrequencyCountAndPriceCount);
    }
}
