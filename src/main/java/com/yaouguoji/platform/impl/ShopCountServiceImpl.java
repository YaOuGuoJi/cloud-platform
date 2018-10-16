package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.constant.OrderStatus;
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
    public List<ShopCountDTO> ordersFinished(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> entities = shopCountMapper.ordersFinished(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(entities, ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> ordersCanceled(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> entities = shopCountMapper.ordersCanceled(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(entities, ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> ordersByHours(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> entities = shopCountMapper.ordersByHours(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(entities, ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> ordersByMonthOrDay(int shopId, int year, int month) {
        List<ShopCountEntity> orderNumAndAmount = shopCountMapper.ordersByMonthOrDay(shopId, year, month, OrderStatus.COMPLETE);
        return BeansListUtils.copyListProperties(orderNumAndAmount, ShopCountDTO.class);
    }

    @Override
    public List<ShopCountDTO> goodsSellTypeCount(int shopId, Date startTime, Date endTime) {
        List<ShopCountEntity> entities = shopCountMapper.goodsSellTypeCount(shopId, startTime, endTime);
        return BeansListUtils.copyListProperties(entities, ShopCountDTO.class);
    }

}
