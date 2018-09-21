package com.yaouguoji.platform.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yaouguoji.platform.constant.OrderRankType;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.OrderRecordJsonDTO;
import com.yaouguoji.platform.entity.OrderNumberEntity;
import com.yaouguoji.platform.entity.OrderRecordEntity;
import com.yaouguoji.platform.entity.OrderRecordJsonEntity;
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
import java.util.stream.Collectors;

/**
 * @author liuwen
 */
@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Resource
    private OrderRecordMapper orderRecordMapper;

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

    /**
     * 查询该用户所有的订单
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<OrderRecordJsonDTO> findOrderRecordByUserId(String userId, String year) {
        List<OrderRecordJsonEntity> list = orderRecordMapper.findOrderRecordByUserId(userId, year);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return BeansListUtils.copyListProperties(list, OrderRecordJsonDTO.class);
    }

}
