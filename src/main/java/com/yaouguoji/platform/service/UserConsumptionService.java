package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.UserConsumptionDTO;

import java.util.Date;
import java.util.List;

public interface UserConsumptionService {
    /**
     * 线上消费统计
     * @return
     */
    UserConsumptionDTO onlineCSP(int userId);
    /**
     * 线下消费
     */
    UserConsumptionDTO shopCSP(int userId);

    /**
     * 年度消费账单
     */
    int recentYearConsumption(int userId);
    /**
     * 用户消费记录
     */
    OrderRecordDTO getUserConsumptionOrders(int userId, Date startDate, Date endDate);

}
