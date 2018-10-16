package com.yaouguoji.platform.mapper;


import com.yaouguoji.platform.entity.ShopCountEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ShopCountMapper {

    /**
     * 商品销售价格统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> salesAndOrdrs(@Param("shopId") int shopId,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    /**
     * 订单统计（包含未发货）
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> playOrdersAndTotal(@Param("shopId") int shopId,
                                               @Param("startTime") Date startTime,
                                               @Param("endTime") Date endTime);



    /**
     * 每天每个时间段销量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> timeQuantumSalesAndOrder(@Param("shopId") int shopId,
                                                         @Param("startTime") Date startTime,
                                                         @Param("endTime") Date endTime);

    /**
     * 月或日销售额及订单量统计
     * @param shopId
     * @param year
     * @param month
     * @return
     */
    List<ShopCountEntity> orderNumAndAmount(@Param("shopId") int shopId,
                                              @Param("year") String year,
                                              @Param("month") String month,
                                            @Param("status") String status);
    /**
     * 时间段内商品销售类别统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> goodsSellTypeCount(@Param("shopId") int shopId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);
}
