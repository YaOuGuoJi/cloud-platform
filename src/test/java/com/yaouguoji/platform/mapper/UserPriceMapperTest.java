package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserPriceCountEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPriceMapperTest {
    @Resource
    private UserPriceMapper userPriceMapper;

    @Test
    public void selectUserFrequencyCountAndPriceCountEntityTest() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        Double ratio = 0.3;
        Map userAverageAndTotalPrice = userPriceMapper.selectUserAverageAndTotalPrice(100001, startTime, endTime);
        BigDecimal averagePrice = (BigDecimal)userAverageAndTotalPrice.get("averagePrice");
        UserPriceCountEntity userPriceCountEntity = userPriceMapper.selectUserPriceSplit(100001, startTime, endTime, averagePrice, ratio);
        Assert.assertNotNull(userPriceCountEntity);
    }
}
