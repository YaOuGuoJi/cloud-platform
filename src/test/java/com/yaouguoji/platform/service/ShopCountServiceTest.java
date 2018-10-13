package com.yaouguoji.platform.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCountServiceTest {
    @Resource
    private ShopCountService shopCountService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Test
    public void goodsSalesCountTest() {
     /*   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = "2018-09-01 09:33:35";
        Date start = sdf.parse(startTime);
        Date end = sdf.parse("2018-09-01 09:33:35");*/
     Date startTime=new Date("2010/10/10 00:00:00");
     Date endTime=new Date();
        Assert.assertNotNull(shopCountService.SalesAndOrdrsCount(100001, startTime,endTime));
    }
    @Test
    public void goodsPlayOrdersCountTest(){
        Date startTime=new Date("2010/10/10 00:00:00");
        Date endTime=new Date();
        Assert.assertNotNull(shopCountService.playOrdersAndTotalCount(100001, startTime,endTime));
    }
    @Test
    public void goodsStrikeCountTest(){
        Date startTime=new Date("2010/10/10 00:00:00");
        Date endTime=new Date();
        Assert.assertNotNull(shopCountService.goodsStrikeCount(100001, startTime,endTime));
    }
    @Test
    public void timeSectionSalesAndVolumeCountTest(){
        Date startTime=new Date("2017/10/10 00:00:00");
        Date endTime=new Date();
        Assert.assertNotNull(shopCountService.timeQuantumSalesAndOrderCount(100001, startTime,endTime));
    }
    @Test
    public void everydayVolumeCountTest(){
        Date startTime=new Date("2017/10/10 00:00:00");
        Date endTime=new Date();
        Assert.assertNotNull(shopCountService.oneDaySalesAndOrdersCount(100001, startTime,endTime));
    }
    @Test
    public void shopGoodsSellTypeCountTest(){
        Date startTime=new Date("2017/10/10 00:00:00");
        Date endTime=new Date();
        Assert.assertNotNull(shopCountService.goodsSellTypeCount(100001, startTime,endTime));
    }
}
