package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopInfoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopInfoServiceTest {

    @Resource
    private ShopInfoService shopInfoService;

    @Test
    public void insertAndDeleteTest() {
        String[] phones={"13","17","15","18","16"};
        Random rd = new Random();
        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        shopInfoDTO.setShopId(30);
        shopInfoDTO.setShopUuid("");
        shopInfoDTO.setShopName("H&M");
        shopInfoDTO.setBranchName("西安亚欧国际店");
        int areaId = rd.nextInt(7)+1;
        shopInfoDTO.setRegionId(areaId);
        int brandId = rd.nextInt(5)+1;
        shopInfoDTO.setBrandId(brandId);
        shopInfoDTO.setShopType("运动品牌");
        shopInfoDTO.setAddress("亚欧风情小镇1号楼111");
        shopInfoDTO.setShopLogo("http://www.logoids.com/upload/image/201807/15307576773935926.jpg");
        shopInfoDTO.setShopOwner("闫蕊");
//        shopInfoDTO.setPhoneNo(phones[(int)(Math.random()*4)]+(int)(Math.random()*10)+"*****"+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10));
        shopInfoDTO.setPhoneNo("15991183772");
        shopInfoDTO.setPhoneNo2(phones[(int)(Math.random()*4)]+(int)(Math.random()*10)+"*****"+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10));
        int price = rd.nextInt(300)+1;
        shopInfoDTO.setAvgPrice(new BigDecimal(price));
        shopInfoDTO.setDeleted(0);

        int shopId = shopInfoService.insertShopInfo(shopInfoDTO);

        Assert.assertTrue(shopId > 1);
        int i = shopInfoService.deleteShopInfoByShopId(shopId);
        Assert.assertEquals(1, i);
        ShopInfoDTO shopInfoDTO1 = shopInfoService.findShopInfoByShopId(shopId);
        Assert.assertNull(shopInfoDTO1);
    }

    @Test
    public void updateShopInfoByIdTest() {
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByShopId(100003);
        shopInfoDTO.setShopName("H&M");
        int i = shopInfoService.updateShopInfoById(shopInfoDTO);
        Assert.assertEquals(i, 1);
    }
}
