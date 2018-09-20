package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.UserConsumptionDTO;
import com.yaouguoji.platform.entity.OrderRecordEntity;
import com.yaouguoji.platform.entity.UserConsumptionEntity;
import com.yaouguoji.platform.mapper.UserConsumptionMapper;
import com.yaouguoji.platform.mapper.UserConsumptionOrderMapper;
import com.yaouguoji.platform.service.UserConsumptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserConsumptionServiceImpl implements UserConsumptionService {
    @Resource
    private UserConsumptionMapper userConsumptionMapper;
    @Resource
    private UserConsumptionOrderMapper userConsumptionOrderMapper;
    @Override
    public UserConsumptionDTO onlineCSP(int userId){
        UserConsumptionEntity userConsumptionEntity = userConsumptionMapper.queryOnlineCSP(userId);
        if(userConsumptionEntity == null){
            return null;
        }
        UserConsumptionDTO userConsumptionDTO = new UserConsumptionDTO();
        BeanUtils.copyProperties(userConsumptionEntity,userConsumptionDTO);
        return userConsumptionDTO;
    }
    @Override
    public UserConsumptionDTO shopCSP(int userId){
        UserConsumptionEntity userConsumptionEntity = userConsumptionMapper.queryShopCSP(userId);
        if(userConsumptionEntity == null){
            return null;
        }
        UserConsumptionDTO userConsumptionDTO = new UserConsumptionDTO();
        BeanUtils.copyProperties(userConsumptionEntity,userConsumptionDTO);
        return userConsumptionDTO;
    }

    @Override
    public BigDecimal recentYearConsumption(Integer userId){
        if(userId == null){
            return null;
        }
        return userConsumptionMapper.recentYearCSP(userId);
    }
    @Override
    public OrderRecordDTO getUserConsumptionOrders(int userId,Date startDate,Date endDate){
        OrderRecordEntity orderRecordEntity = userConsumptionOrderMapper.getUserConsumptionOrders(userId,startDate,endDate);
        if (orderRecordEntity == null) {
            return null;
        }
        OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
        BeanUtils.copyProperties(orderRecordEntity,orderRecordDTO);
        return orderRecordDTO;
    }


}
