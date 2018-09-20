package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserConsumptionEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


public interface UserConsumptionMapper {
    /**
     * 线上消费查询
     * @param userId
     * @return
     */
    UserConsumptionEntity queryOnlineCSP(@Param("userId") int userId);
    /**
     * 线下消费查询
     */
    UserConsumptionEntity queryShopCSP(@Param("userId") int userId);

    /**
     * 用户年消费
     */
    BigDecimal recentYearCSP(@Param("userId") int userId);
    /**
     * 用户消费清单
     */
}
