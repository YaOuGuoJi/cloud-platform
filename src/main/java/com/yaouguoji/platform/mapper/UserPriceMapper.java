package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserPriceCountEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface UserPriceMapper {
    /**
     * 根据日期以及商铺的id查该商铺消费者的消费金额分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    UserPriceCountEntity selectUserPriceSplit(@Param("shopId") Integer shopId,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime,
                                              @Param("average") BigDecimal averagePrice,
                                              @Param("ratio") Double ratio);

    /**
     * 根据日期以及商铺id查该店铺的所有消费者的平均消费水平以及总的销售额
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    Map selectUserAverageAndTotalPrice(@Param("shopId") Integer shopId,
                                       @Param("startTime") Date startTime,
                                       @Param("endTime") Date endTime);
}
