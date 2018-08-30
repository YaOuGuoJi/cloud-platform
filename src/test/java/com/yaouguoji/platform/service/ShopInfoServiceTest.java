package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopInfoServiceTest {

    @Resource
    private ShopInfoService shopInfoService;

    @Test
    public void testLoadById() {
        ShopDTO shopDTO = shopInfoService.getShopInfoByShopId(4);
        Assert.assertTrue(shopDTO != null && "Nike".equals(shopDTO.getShopName()));
    }

    @Test
    public void testUpdate() {
        ShopDTO shopDTO = shopInfoService.getShopInfoByShopId(4);
        shopDTO.setShopName("aaaaaaaaaaaaaaaaaaaaaa");
        shopDTO.setBrandId(10);
        shopDTO.setRegionId(2);
        int i = shopInfoService.updateShopInfo(shopDTO);
        Assert.assertEquals(1, i);
    }

}
