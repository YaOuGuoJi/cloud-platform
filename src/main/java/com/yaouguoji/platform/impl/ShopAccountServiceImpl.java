package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ShopAccountDTO;
import com.yaouguoji.platform.entity.ShopAccountEntity;
import com.yaouguoji.platform.mapper.ShopAccountMapper;
import com.yaouguoji.platform.service.ShopAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/12/4
 */
@Service
public class ShopAccountServiceImpl implements ShopAccountService {

    @Resource
    private ShopAccountMapper shopAccountMapper;

    @Override
    public ShopAccountDTO findShopAccountByShopId(Integer shopId) {
        if (shopId == null || shopId <= 0) {
            return null;
        }
        ShopAccountEntity entity = shopAccountMapper.findByShopId(shopId);
        if (entity == null) {
            return null;
        }
        ShopAccountDTO shopAccountDTO = new ShopAccountDTO();
        BeanUtils.copyProperties(entity, shopAccountDTO);
        return shopAccountDTO;
    }

    @Override
    public Integer addShopAccount(ShopAccountDTO shopAccountDTO) {
        if (shopAccountDTO == null || shopAccountDTO.getShopId() <= 0
                || StringUtils.isEmpty(shopAccountDTO.getPassword())) {
            return 0;
        }
        ShopAccountEntity entity = new ShopAccountEntity();
        return shopAccountMapper.addShopAccount(entity);
    }
}
