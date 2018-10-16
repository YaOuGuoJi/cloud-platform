package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.GoodsSellTypeDTO;
import com.yaouguoji.platform.dto.SalesAndPlayOrderDTO;
import com.yaouguoji.platform.dto.SalesDTO;
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
    public List<SalesDTO> SalesAndOrdrsCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> SalesAndOrdrsCountDatas = shopCountMapper.salesAndOrdrs(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(SalesAndOrdrsCountDatas, SalesDTO.class);
    }

    @Override
    public List<SalesDTO> playOrdersAndTotalCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> playOrdersAndTotalCountDatas = shopCountMapper.playOrdersAndTotal(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(playOrdersAndTotalCountDatas,SalesDTO.class);
    }

    @Override
    public List<ShopCountDTO> goodsStrikeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> goodsStrikeDatas = shopCountMapper.goodsStrikeCount(shopId, startTime, endTime);

        return BeansListUtils.copyListProperties(goodsStrikeDatas,ShopCountDTO.class);
    }

    @Override
    public List<SalesAndPlayOrderDTO> timeQuantumSalesAndOrderCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> timeQuantumSalesAndOrderDatas = shopCountMapper.timeQuantumSalesAndOrder(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(timeQuantumSalesAndOrderDatas, SalesAndPlayOrderDTO.class);
    }

    @Override
    public List<SalesAndPlayOrderDTO> oneDaySalesAndOrdersCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> oneDaySalesAndOrdersDatas = shopCountMapper.oneDaySalesAndOrders(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(oneDaySalesAndOrdersDatas,SalesAndPlayOrderDTO.class);
    }

    @Override
    public List<GoodsSellTypeDTO> goodsSellTypeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> goodsSellTypeCountDatas = shopCountMapper.goodsSellTypeCount(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(goodsSellTypeCountDatas, GoodsSellTypeDTO.class);
    }

}
