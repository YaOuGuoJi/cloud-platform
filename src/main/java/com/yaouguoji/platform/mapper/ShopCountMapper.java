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
    List<ShopCountEntity> goodsSalesCount(@Param("shopId") int shopId,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    /**
     * 订单统计（包含未发货）
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> goodsPlayOrdersCount(@Param("shopId") int shopId,
                                               @Param("startTime") Date startTime,
                                               @Param("endTime") Date endTime);

    /**
     * 实际销售量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> goodsStrikeCount(@Param("shopId") int shopId,
                                           @Param("startTime") Date startTime,
                                           @Param("endTime") Date endTime);

    /**
     * 每天每个时间段销量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> timeSectionSalesAndVolumeCount(@Param("shopId") int shopId,
                                                         @Param("startTime") Date startTime,
                                                         @Param("endTime") Date endTime);

    /**
     * 时间段内商铺日销售额统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountEntity> everydayVolumeCount(@Param("shopId") int shopId,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    /**
     * 时间段内内商品销售类别统计
     * @param shopId
     * @return
     */
    List<ShopCountEntity> shopGoodsSellTypeCount(@Param("shopId") int shopId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);
}
