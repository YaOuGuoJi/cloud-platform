package com.yaouguoji.platform.impl;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.constant.ShopOrderRankType;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.entity.OrderRecordEntity;
import com.yaouguoji.platform.entity.ShopOrderNumberEntity;
import com.yaouguoji.platform.mapper.OrderRecordMapper;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Override
    public Map<Integer, Object> findShopIdsRankByOrders(int limit, Date startTime, Date endTime, int type) {
        if (limit <= 0 || startTime.after(endTime)) {
            return Collections.emptyMap();
        }
        List<ShopOrderNumberEntity> orderNumberEntities;
        if (type == ShopOrderRankType.ORDER_NUM_COUNT) {
            orderNumberEntities = orderRecordMapper.findOrderNumTop(limit, startTime, endTime);
        } else if (type == ShopOrderRankType.ORDER_PRICE_COUNT) {
            orderNumberEntities = orderRecordMapper.findOrderPriceTop(limit, startTime, endTime);
        } else {
            return Collections.emptyMap();
        }
        Map<Integer, Object> resultMap = Maps.newHashMap();
        orderNumberEntities.forEach(shopOrderNumberEntity -> {
            resultMap.put(shopOrderNumberEntity.getShopId(), shopOrderNumberEntity.getResult());
        });
        return resultMap;
    }

    @Override
    public OrderRecordDTO findOrderDetailsByOrderId(int orderId) {
        OrderRecordEntity entity = orderRecordMapper.selectById(orderId);
        if (entity == null) {
            return null;
        }
        OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
        BeanUtils.copyProperties(entity, orderRecordDTO);
        return orderRecordDTO;
    }

    @Override
    public List<OrderRecordDTO> findOrdersByUserId(int userId, Date startTime, Date endTime) {
        List<OrderRecordEntity> entityList = orderRecordMapper.selectOrderRecordsByUserId(userId, startTime, endTime);
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return BeansListUtils.copyListProperties(entityList, OrderRecordDTO.class);
    }

    @Override
    public List<OrderRecordDTO> findOrdersByShopId(int shopId, Date startTime, Date endTime) {
        List<OrderRecordEntity> entityList = orderRecordMapper.selectOrderRecordsByShopId(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return BeansListUtils.copyListProperties(entityList, OrderRecordDTO.class);
    }

    @Override
    public int addOrderInfo(OrderRecordDTO orderRecordDTO) {
        if (orderRecordDTO == null) {
            return 0;
        }
        OrderRecordEntity entity = new OrderRecordEntity();
        BeanUtils.copyProperties(orderRecordDTO, entity);
        orderRecordMapper.addOrderInfo(entity);
        return entity.getOrderId();
    }
}
