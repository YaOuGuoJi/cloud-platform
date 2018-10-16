package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopInfoDTO;

import java.util.List;

/**
 * 商户信息服务
 *
 * @author liuwen
 */
public interface ShopInfoService {

    /**
     * 查询所有商家信息
     *
     * @return
     */
    List<ShopInfoDTO> findAll();

    /**
     * 批量查询
     *
     * @param shopIdList
     * @return
     */
    List<ShopInfoDTO> batchFindByShopIdList(List<Integer> shopIdList);

    /**
     * 根据shopId查询商户详细信息
     *
     * @param shopId
     * @return
     */
    ShopInfoDTO findShopInfoByShopId(int shopId);

    /**
     * 插入商户详细信息
     *
     * @param shopInfoDTO
     * @return
     */
    int insertShopInfo(ShopInfoDTO shopInfoDTO);

    /**
     * 更新商户信息
     *
     * @param shopInfoDTO
     * @return
     */
    int updateShopInfoById(ShopInfoDTO shopInfoDTO);

    /**
     * 根据商户id删除商户信息
     *
     * @param shopId
     * @return
     */
    int deleteShopInfoByShopId(int shopId);
}
