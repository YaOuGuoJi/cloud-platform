package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.OrderRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderRecordMapper {

    /**
     * 根据orderId查询订单
     * @param orderId
     * @return
     */
    OrderRecordEntity selectById(@Param("orderId") int orderId);

    /**
     * 新增订单信息
     * @param orderRecordEntity
     * @return
     */
    int addOrderInfo(@Param("orderRecordEntity") OrderRecordEntity orderRecordEntity);

    /**
     * 更新订单信息
     * @param orderRecordEntity
     * @return
     */
    int updateOrderInfo(@Param("orderRecordEntity") OrderRecordEntity orderRecordEntity);

    /**
     * 根据userId查询订单记录
     * @param userId
     * @return
     */
    List<OrderRecordEntity> selectOrderRecordsByUserId(@Param("userId") int userId,
                                                       @Param("start") Date startTime,
                                                       @Param("end") Date endTime);

    /**
     * 查询shopId一段时间内的订单记录
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderRecordEntity> selectOrderRecordsByShopId(@Param("shopId") int shopId,
                                                       @Param("start") Date startTime,
                                                       @Param("end") Date endTime);
}
