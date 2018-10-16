package com.yaouguoji.platform.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCountMappTest {
    @Resource
    private ShopCountMapper shopCountMapper;
    @Test
    public void shopGoodsSellTypeCountTest(){
        //shopCountMapper.shopGoodsSellTypeCount(100007,2018-01-01 00:00:00,2018/12/01 00:00:00);
    }

}
