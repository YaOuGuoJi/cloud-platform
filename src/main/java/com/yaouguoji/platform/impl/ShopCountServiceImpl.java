package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ShopCountDTO;
import com.yaouguoji.platform.entity.ShopCountEntity;
import com.yaouguoji.platform.mapper.ShopCountMapper;
import com.yaouguoji.platform.service.ShopCountService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShopCountServiceImpl implements ShopCountService {
    @Resource
    private ShopCountMapper shopCountMapper;

    @Override
    public List<ShopCountDTO> goodsSalesCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> goodsSalesCountDatas = shopCountMapper.goodsSalesCount(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(goodsSalesCountDatas, ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> goodsPlayOrdersCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> goodsPlayOrdersDatas = shopCountMapper.goodsPlayOrdersCount(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(goodsPlayOrdersDatas,ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> goodsStrikeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> goodsStrikeDatas = shopCountMapper.goodsStrikeCount(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(goodsStrikeDatas,ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> timeSectionSalesAndVolumeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> timeSectionSalesAndVolumeDatas = shopCountMapper.timeSectionSalesAndVolumeCount(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(timeSectionSalesAndVolumeDatas,ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> everydayVolumeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> everydayVolumeDatas = shopCountMapper.everydayVolumeCount(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(everydayVolumeDatas,ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> shopGoodsSellTypeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> shopGoodsSellTypeDatas = shopCountMapper.shopGoodsSellTypeCount(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(shopGoodsSellTypeDatas,ShopCountDTO.class);
    }

}
