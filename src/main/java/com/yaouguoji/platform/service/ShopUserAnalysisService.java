package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserPriceCountDTO;

import java.util.Date;
import java.util.Map;

public interface ShopUserAnalysisService {
    /**
     * 根据日期以及商铺的id查该商铺顾客的每个年龄段的男女人数以及性别该商铺总的男女人数
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, Map<String, Integer>> selectAgeAndSexSplit(Integer shopId, Date startTime, Date endTime);

    /**
     * 根据日期以及商铺的id查该商铺顾客的消费额度分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    UserPriceCountDTO selectUserPriceCount(Integer shopId, Date startTime, Date endTime);

    /**
     * 根据商铺id和日期查询在该商铺消费的顾客消费次数分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, Object> selectUserFrequencyCount(Integer shopId, Date startTime, Date endTime);
}
