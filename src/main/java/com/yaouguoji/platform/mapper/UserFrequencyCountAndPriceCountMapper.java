package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserFrequencyCountAndPriceCountEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserFrequencyCountAndPriceCountMapper {
    /**
     * 根据日期以及商铺的id查该商铺每个顾客的消费金额以及消费次数
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserFrequencyCountAndPriceCountEntity> selectUserFrequencyCountAndPriceCount(@Param("shopId") Integer shopId,
                                                                                      @Param("startTime") Date startTime,
                                                                                      @Param("endTime") Date endTime);
}
