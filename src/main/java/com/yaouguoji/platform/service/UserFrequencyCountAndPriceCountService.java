package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserFrequencyCountAndPriceCountDTO;

import java.util.Date;
import java.util.List;

public interface UserFrequencyCountAndPriceCountService {
    /**
     * 根据日期以及商铺的id查该商铺顾客的消费次数分布以及消费额度分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    UserFrequencyCountAndPriceCountDTO selectUserFrequencyCountAndPriceCount(Integer shopId, Date startTime, Date endTime);
}

