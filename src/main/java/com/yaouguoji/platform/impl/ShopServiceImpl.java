package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ShopDTO;
import com.yaouguoji.platform.entity.ShopEntity;
import com.yaouguoji.platform.mapper.ShopMapper;
import com.yaouguoji.platform.service.ShopService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Resource
    private ShopMapper shopMapper;

    @Override
    public ShopDTO getShopInfoByShopId(int shopId) {
        ShopEntity shopEntity = shopMapper.findShopByShopId(shopId);
        if (shopEntity == null) {
            return null;
        }
        ShopDTO shopDTO = new ShopDTO();
        BeanUtils.copyProperties(shopEntity, shopDTO);
        return shopDTO;
    }

    @Override
    public int insertShopInfo(ShopDTO shopDTO) {
        if (shopDTO == null) {
            return 0;
        }
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setShopName(shopDTO.getShopName());
        shopEntity.setBrandId(shopDTO.getBrandId());
        shopEntity.setRegionId(shopDTO.getRegionId());
        shopMapper.insert(shopEntity);
        return shopEntity.getId();
    }

    @Override
    public void deleteShopInfo(int shopId) {
        if (shopId <= 0) {
            return;
        }
        shopMapper.deleteShopInfoById(shopId);
        LOGGER.info("shop: [{}] has been deleted.", shopId);
    }

    @Override
    public int updateShopInfo(ShopDTO shopDTO) {
        if (shopDTO == null || shopDTO.getId() <= 0
                || StringUtils.isBlank(shopDTO.getShopName())) {
            return 0;
        }
        ShopEntity shopEntity = new ShopEntity();
        BeanUtils.copyProperties(shopDTO, shopEntity);
        return shopMapper.updateShopInfo(shopEntity);
    }


}
