package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopAccountDTO;

/**
 * @author liuwen
 * @date 2018/12/4
 */
public interface ShopAccountService {

    /**
     * 查询商户账号
     *
     * @param shopId
     * @return
     */
    ShopAccountDTO findShopAccountByShopId(Integer shopId);

    /**
     * 插入商户账号
     *
     * @param shopAccountDTO
     * @return
     */
    Integer addShopAccount(ShopAccountDTO shopAccountDTO);
}
