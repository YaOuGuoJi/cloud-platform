package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ShopEntity;
import org.apache.ibatis.annotations.Param;

public interface ShopMapper {

    /**
     * 新增商户信息
     * @param shopEntity
     * @return
     */
    int insert(@Param("shopEntity") ShopEntity shopEntity);

    /**
     * 根据商户id查询商户信息
     * @param shopId
     * @return
     */
    ShopEntity findShopByShopId(@Param("shopId") int shopId);

    /**
     * 根据商户id删除信息
     * @param id
     */
    void deleteShopInfoById(@Param("id") int id);

    /**
     * 修改商户信息
     * @param shopEntity
     * @return
     */
    int updateShopInfo(@Param("shopEntity") ShopEntity shopEntity);
}
