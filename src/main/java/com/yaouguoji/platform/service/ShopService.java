package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopDTO;

/**
 * 测试类
 */
public interface ShopService {

    /**
     * 根据商户名获取商户信息
     * @param shopId
     * @return
     */
    ShopDTO getShopInfoByShopId(int shopId);

    /**
     * 插入商户信息
     * @param shopDTO
     * @return
     */
    int insertShopInfo(ShopDTO shopDTO);

    /**
     * 删除商户信息
     * @param shopId
     */
    void deleteShopInfo(int shopId);

    /**
     * 更新商户信息
     * @param shopDTO
     * @return
     */
    int updateShopInfo(ShopDTO shopDTO);
}
