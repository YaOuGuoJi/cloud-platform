package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopInfoDTO;

/**
 * 商户信息服务
 */
public interface ShopInfoService {

    /**
     * 根据shopId查询商户详细信息
     * @param shopId
     * @return
     */
    ShopInfoDTO findShopInfoByShopId(int shopId);

    /**
     * 插入商户详细信息
     * @param shopInfoDTO
     * @return
     */
    int insertShopInfo(ShopInfoDTO shopInfoDTO);

    /**
     * 更新商户信息
     * @param shopInfoDTO
     * @return
     */
    int updateShopInfoById(ShopInfoDTO shopInfoDTO);

    /**
     * 根据商户id删除商户信息
     * @param shopId
     * @return
     */
    int deleteShopInfoByShopId(int shopId);
}
