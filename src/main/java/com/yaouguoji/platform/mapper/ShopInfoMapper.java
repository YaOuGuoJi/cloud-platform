package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ShopInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuwen
 */
public interface ShopInfoMapper {

    /**
     * 查询列表
     *
     * @param shopIdList
     * @return
     */
    List<ShopInfoEntity> findByShopIdList(@Param("shopIdList") List<Integer> shopIdList);

    /**
     * 增
     *
     * @param shopInfoEntity
     * @return
     */
    void addShopInfo(@Param("shopInfoEntity") ShopInfoEntity shopInfoEntity);

    /**
     * 软删除
     *
     * @param shopId
     * @return
     */
    int deleteShopInfo(@Param("shopId") int shopId);

    /**
     * 改
     *
     * @param shopInfoEntity
     * @return
     */
    int updateShopInfo(@Param("shopInfoEntity") ShopInfoEntity shopInfoEntity);

    /**
     * 查询
     *
     * @param shopId
     * @return
     */
    ShopInfoEntity findShopInfoById(@Param("shopId") int shopId);


}
