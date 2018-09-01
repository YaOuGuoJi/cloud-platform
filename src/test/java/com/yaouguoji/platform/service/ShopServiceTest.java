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
public class ShopServiceTest {

    @Resource
    private ShopService shopService;

    @Test
    public void testLoadById() {
        ShopDTO shopDTO = shopService.getShopInfoByShopId(4);
        Assert.assertTrue(shopDTO != null && "Nike".equals(shopDTO.getShopName()));
    }

    @Test
    public void testUpdate() {
        ShopDTO shopDTO = shopService.getShopInfoByShopId(4);
        shopDTO.setShopName("aaaaaaaaaaaaaaaaaaaaaa");
        shopDTO.setBrandId(10);
        shopDTO.setRegionId(2);
        int i = shopService.updateShopInfo(shopDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void testAdd() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(100);
        shopDTO.setShopName("Nike");
        shopDTO.setBrandId(20);
        shopDTO.setRegionId(21);
        int i = shopService.insertShopInfo(shopDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void testDelete() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopName("测试商户");
        shopDTO.setBrandId(2);
        shopDTO.setRegionId(4);
        int i = shopService.insertShopInfo(shopDTO);

        shopService.deleteShopInfo(i);
    }

}
