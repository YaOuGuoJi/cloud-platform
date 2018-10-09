package com.yaouguoji.platform.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yaouguoji.platform.constant.OrderRankType;
import com.yaouguoji.platform.dto.ObjectMapDTO;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.OrderRecordRequest;
import com.yaouguoji.platform.entity.OrderNumberEntity;
import com.yaouguoji.platform.entity.OrderRecordEntity;
import com.yaouguoji.platform.mapper.OrderRecordMapper;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuwen
 */
@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Override
    public List<ObjectMapDTO<Integer, Object>> findAreaShopRankByType(OrderRecordRequest request) {
        Assert.notNull(request, "请求不能为空！");
        Assert.isTrue(request.getLimit() > 0 && request.getId() > 0, "返回记录数和areaId必须大于0！");
        Assert.isTrue(request.getStartTime().before(request.getEndTime()), "结束时间不得早于开始时间！");

        List<ObjectMapDTO<Integer, Object>> result = Lists.newArrayList();
        for (int i = 0; i < request.getLimit(); i++) {
            result.add(new ObjectMapDTO<>(10000 + i, i * 1000));
        }
        return result;
    }

    @Override
    public Map<Integer, Object> findAreaOrderNumber(Date startTime, Date endTime, int type) {
        List<OrderNumberEntity> areaOrderNum = null;
        if (type == OrderRankType.ORDER_NUM_COUNT) {
            areaOrderNum = orderRecordMapper.findAreaOrderNum(startTime, endTime);
        } else if (type == OrderRankType.ORDER_PRICE_COUNT) {
            areaOrderNum = orderRecordMapper.findAreaOrderPrice(startTime, endTime);
        }
        if (CollectionUtils.isEmpty(areaOrderNum)) {
            return Collections.emptyMap();
        }
        return areaOrderNum.stream().collect(Collectors.toMap(OrderNumberEntity::getId, OrderNumberEntity::getResult));
    }

    @Override
    public PageInfo<OrderRecordDTO> pageFindOrderRecordByShopId(int shopId, int pageNum, int pageSize,
                                                                Date startTime, Date endTime) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<OrderRecordEntity> entityList = orderRecordMapper.selectOrderRecordsByShopId(shopId, startTime, endTime);
        return BeansListUtils.copyListPageInfo(entityList, OrderRecordDTO.class);
    }

    @Override
    public PageInfo<OrderRecordDTO> pageFindOrderRecordByUserId(int userId, int pageNum, int pageSize,
                                                                Date startTime, Date endTime) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<OrderRecordEntity> entityList = orderRecordMapper.selectOrderRecordsByUserId(userId, startTime, endTime);
        return BeansListUtils.copyListPageInfo(entityList, OrderRecordDTO.class);
    }

    @Override
    public Map<Integer, Object> findShopIdsRankByOrders(int limit, Date startTime, Date endTime, int type) {
        if (limit <= 0 || startTime.after(endTime)) {
            return Collections.emptyMap();
        }
        List<OrderNumberEntity> orderNumberEntities = null;
        if (type == OrderRankType.ORDER_NUM_COUNT) {
            orderNumberEntities = orderRecordMapper.findOrderNumTop(limit, startTime, endTime);
        } else if (type == OrderRankType.ORDER_PRICE_COUNT) {
            orderNumberEntities = orderRecordMapper.findOrderPriceTop(limit, startTime, endTime);
        }
        if (CollectionUtils.isEmpty(orderNumberEntities)) {
            return Collections.emptyMap();
        }
        return orderNumberEntities.stream().collect(Collectors
                .toMap(OrderNumberEntity::getId, OrderNumberEntity::getResult));
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
