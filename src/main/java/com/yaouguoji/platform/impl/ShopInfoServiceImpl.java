package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ShopDTO;
import com.yaouguoji.platform.entity.ShopEntity;
import com.yaouguoji.platform.mapper.ShopMapper;
import com.yaouguoji.platform.service.ShopInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ShopDTO getShopInfoByShopId(int shopId) {
        ShopDTO shopDTO = new ShopDTO();
        ShopEntity shopEntity = shopMapper.getShopByShopId(shopId);
        if (shopEntity == null || shopEntity.getShopId() <= 0) {
            return null;
        }
        BeanUtils.copyProperties(shopEntity, shopDTO);
        return shopDTO;
    }
}
