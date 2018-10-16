package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserFrequencyEntity;
import com.yaouguoji.platform.entity.UserPriceCountEntity;
import com.yaouguoji.platform.entity.UserSexAndAgeEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ShopUserAnalysisMapper {

    /**
     * 根据日期以及商铺的id查该商铺顾客的每个年龄的每个性别人数
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserSexAndAgeEntity> ageAndSexSplit(@Param("shopId") Integer shopId,
                                             @Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);

    /**
     * 根据商铺的id以及时间查询消费次数分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserFrequencyEntity> userFrequencyCount(@Param("shopId") Integer shopId,
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
    Map<String, BigDecimal> averageAndTotalFrequency(@Param("shopId") Integer shopId,
                                                     @Param("startTime") Date startTime,
                                                     @Param("endTime") Date endTime);

    /**
     * 根据日期以及商铺的id查该商铺消费者的消费金额分布
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @param low
     * @param high
     * @return
     */
    UserPriceCountEntity userPriceCount(@Param("shopId") Integer shopId,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime,
                                        @Param("low") BigDecimal low,
                                        @Param("high") BigDecimal high);

    /**
     * 根据日期以及商铺id查该店铺的所有消费者的平均消费水平以及总的销售额
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, BigDecimal> averageAndTotalPrice(@Param("shopId") Integer shopId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);
}
