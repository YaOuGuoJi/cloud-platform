package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ShopCountEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ShopCountMapper {

    /**
     * 商户时间段内订单成交统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> ordersFinished(@Param("shopId") int shopId,
                                         @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime);

    /**
     * 商户时间段内已取消订单统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> ordersCanceled(@Param("shopId") int shopId,
                                         @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime);

    /**
     * 商户时间段内订单按小时统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> ordersByHours(@Param("shopId") int shopId,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);

    /**
     * 月或日销售额及订单量统计
     *
     * @param shopId
     * @param year
     * @param month
     * @param status
     * @return
     */
    List<ShopCountEntity> ordersByMonthOrDay(@Param("shopId") int shopId,
                                             @Param("year") int year,
                                             @Param("month") int month,
                                             @Param("status") String status);

    /**
     * 时间段内商品销售类别统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> goodsSellTypeCount(@Param("shopId") int shopId,
                                             @Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);
}
