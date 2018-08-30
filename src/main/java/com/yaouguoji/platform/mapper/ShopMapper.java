package com.yaouguoji.platform.mapper;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.entity.ShopEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 示例代码
 * 正常情况下这里应该用mybatis实现与mapper.xml文件的映射
 */
@Component
public class ShopMapper {

    private List<ShopEntity> shopEntityList;

    public ShopEntity getShopByShopId(int shopId) {
        prepareFakeData();
        for (ShopEntity entity : shopEntityList) {
            if (entity.getShopId() == shopId) {
                return entity;
            }
        }
        return null;
    }

    /**
     * 造点假数据
     */
    private void prepareFakeData() {
        ShopEntity entity1 = new ShopEntity();
        entity1.setShopId(111121);
        entity1.setShopName("阿迪达斯");
        entity1.setBrandId(1);
        entity1.setRegionId(2);

        ShopEntity entity2 = new ShopEntity();
        entity2.setShopId(111123);
        entity2.setShopName("测试商户名");
        entity2.setBrandId(1);
        entity2.setRegionId(2);
        shopEntityList = Lists.newArrayList(entity1, entity2);
    }
}
