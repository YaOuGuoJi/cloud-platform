package com.yaouguoji.platform.service;

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
    public void goodsSalesCount(int shopId, Date startTime, Date endTime){
        shopCountService.goodsSalesCount(shopId,startTime,endTime);

    }
}
