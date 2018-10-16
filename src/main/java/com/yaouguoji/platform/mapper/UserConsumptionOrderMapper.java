package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.OrderRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserConsumptionOrderMapper {
    OrderRecordEntity getUserConsumptionOrders(@Param("userId") int userId,
                                               @Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate);

}
