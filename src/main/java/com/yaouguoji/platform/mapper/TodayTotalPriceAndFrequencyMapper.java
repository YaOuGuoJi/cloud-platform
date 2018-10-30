package com.yaouguoji.platform.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface TodayTotalPriceAndFrequencyMapper {
   Map<String,BigDecimal> todayTotalPriceAndFrequency(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
