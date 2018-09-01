package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.entity.ShopInfoEntity;
import com.yaouguoji.platform.mapper.ShopInfoMapper;
import com.yaouguoji.platform.service.ShopInfoService;
import com.yaouguoji.platform.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {

    @Resource
    private ShopInfoMapper shopInfoMapper;

    @Override
    public ShopInfoDTO findShopInfoByShopId(int shopId) {
        ShopInfoEntity entity = shopInfoMapper.findShopInfoById(shopId);
        if (entity == null) {
            return null;
        }
        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        BeanUtils.copyProperties(entity, shopInfoDTO);
        return shopInfoDTO;
    }

    @Override
    public int insertShopInfo(ShopInfoDTO shopInfoDTO) {
        if (shopInfoDTO == null) {
            return 0;
        }
        shopInfoDTO.setShopId(0);
        shopInfoDTO.setShopUuid(UuidUtil.buildUuid());
        ShopInfoEntity shopInfoEntity = new ShopInfoEntity();
        BeanUtils.copyProperties(shopInfoDTO, shopInfoEntity);
        shopInfoMapper.addShopInfo(shopInfoEntity);
        return shopInfoEntity.getShopId();
    }

    @Override
    public int updateShopInfoById(ShopInfoDTO shopInfoDTO) {
        if (shopInfoDTO == null || shopInfoDTO.getShopId() <= 0) {
            return 0;
        }
        ShopInfoEntity shopInfoEntity = new ShopInfoEntity();
        BeanUtils.copyProperties(shopInfoDTO, shopInfoEntity);
        return shopInfoMapper.updateShopInfo(shopInfoEntity);
    }

    @Override
    public int deleteShopInfoByShopId(int shopId) {
        if (shopId <= 0) {
            return 0;
        }
        return shopInfoMapper.deleteShopInfo(shopId);
    }
}
