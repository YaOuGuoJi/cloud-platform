package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserFrequencyEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserFrequencyMapper {
    /**
     * 根据商铺的id以及时间查询消费次数分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserFrequencyEntity> selectUserFrequency(@Param("shopId") Integer shopId,
                                                  @Param("startTime") Date startTime,
                                                  @Param("endTime") Date endTime);

    /**
     * 根据商铺的id以及时间查询顾客的平均消费次数
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, BigDecimal> selectUserTotalAndAverageFrequency(@Param("shopId") Integer shopId,
                                                               @Param("startTime") Date startTime,
                                                               @Param("endTime") Date endTime);
}
