package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ShopAccountEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 * @date 2018/12/4
 */
public interface ShopAccountMapper {

    /**
     * 根据商户id查询
     *
     * @param shopId
     * @return
     */
    ShopAccountEntity findByShopId(@Param("shopId") int shopId);

    /**
     * 添加
     *
     * @param shopAccountEntity
     * @return
     */
    Integer addShopAccount(@Param("shopAccountEntity") ShopAccountEntity shopAccountEntity);
}
