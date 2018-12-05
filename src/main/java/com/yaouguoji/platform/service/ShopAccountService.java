package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopAccountDTO;

/**
 * @author liuwen
 * @date 2018/12/4
 */
@Deprecated
public interface ShopAccountService {

    /**
     * 查询商户账号
     *
     * @param phoneNum
     * @return
     */
    ShopAccountDTO findShopAccountByShopId(String phoneNum);

    /**
     * 插入商户账号
     *
     * @param shopAccountDTO
     * @return
     */
    Integer addShopAccount(ShopAccountDTO shopAccountDTO);
}
