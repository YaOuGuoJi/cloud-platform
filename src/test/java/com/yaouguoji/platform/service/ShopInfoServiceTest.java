package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopInfoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopInfoServiceTest {

    @Resource
    private ShopInfoService shopInfoService;

    @Test
    public void insertAndDeleteTest() {
        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        shopInfoDTO.setShopId(0);
        shopInfoDTO.setShopUuid("");
        shopInfoDTO.setShopName("测试店名");
        shopInfoDTO.setBranchName("西安亚欧国际店");
        shopInfoDTO.setRegionId(2);
        shopInfoDTO.setBrandId(3);
        shopInfoDTO.setShopType("服饰");
        shopInfoDTO.setAddress("亚欧风情小镇1号楼111");
        shopInfoDTO.setShopLogo("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1535765175&di=0199743b7f771770da0d2a7369dab307&src=http://www.epbiao.com/Public/upload/remote/2017/08/75571501738934.png");
        shopInfoDTO.setShopOwner("刘文");
        shopInfoDTO.setPhoneNo("18799342334");
        shopInfoDTO.setPhoneNo2("13523923344");
        shopInfoDTO.setAvgPrice(new BigDecimal(355.67));
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
