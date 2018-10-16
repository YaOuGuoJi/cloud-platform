package com.yaouguoji.platform.service;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCountServiceTest {
    @Resource
    private ShopCountService shopCountService;

    @Test
    public void goodsSalesCountTest() {
        Date startTime = buildStartTime();
        Date endTime = new Date();
        Assert.assertNotNull(shopCountService.ordersFinished(100001, startTime, endTime));
    }

    @Test
    public void goodsPlayOrdersCountTest() {
        Date startTime = buildStartTime();
        Date endTime = new Date();
        Assert.assertNotNull(shopCountService.ordersCanceled(100001, startTime, endTime));
    }

    @Test
    public void timeSectionSalesAndVolumeCountTest() {
        Date startTime = buildStartTime();
        Date endTime = new Date();
        Assert.assertNotNull(shopCountService.ordersByHours(100001, startTime, endTime));
    }

    @Test
    public void orderNumAndAmountTest() {
        Assert.assertNotNull(shopCountService.ordersByMonthOrDay(100001, 2018, 0));
//        Assert.assertNotNull(shopCountService.ordersByMonthOrDay(100001,"2018","9"));
    }

    @Test
    public void shopGoodsSellTypeCountTest() {
        Date startTime = buildStartTime();
        Date endTime = new Date();
        Assert.assertNotNull(shopCountService.goodsSellTypeCount(100001, startTime, endTime));
    }

    private Date buildStartTime() {
        return new DateTime(2018, 1, 1, 1, 1).toDate();
    }
}
