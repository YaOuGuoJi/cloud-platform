package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.OrderRecordEntity;
import com.yaouguoji.platform.entity.OrderNumberEntity;
import com.yaouguoji.platform.entity.OrderRecordJsonEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author liuwen
 */
public interface OrderRecordMapper {

    /**
     * 查询时间段内各个区域的订单量
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderNumberEntity> findAreaOrderNum(@Param("startTime") Date startTime,
                                                @Param("endTime") Date endTime);

    /**
     * 查询时间段内各个区域的订单总额
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderNumberEntity> findAreaOrderPrice(@Param("startTime") Date startTime,
                                               @Param("endTime") Date endTime);

    /**
     * 查询时间段内订单量最多的商户
     *
     * @param limit
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderNumberEntity> findOrderNumTop(@Param("limit") int limit,
                                            @Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime);

    /**
     * 查询时间段内订单总额最大的商户
     *
     * @param limit
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderNumberEntity> findOrderPriceTop(@Param("limit") int limit,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    /**
     * 根据orderId查询订单
     *
     * @param orderId
     * @return
     */
    OrderRecordEntity selectById(@Param("orderId") int orderId);

    /**
     * 新增订单信息
     *
     * @param orderRecordEntity
     * @return
     */
    int addOrderInfo(@Param("orderRecordEntity") OrderRecordEntity orderRecordEntity);

    /**
     * 更新订单信息
     *
     * @param orderRecordEntity
     * @return
     */
    int updateOrderInfo(@Param("orderRecordEntity") OrderRecordEntity orderRecordEntity);

    /**
     * 根据userId查询订单记录
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderRecordEntity> selectOrderRecordsByUserId(@Param("userId") int userId,
                                                       @Param("start") Date startTime,
                                                       @Param("end") Date endTime);

    /**
     * 查询shopId一段时间内的订单记录
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderRecordEntity> selectOrderRecordsByShopId(@Param("shopId") int shopId,
                                                       @Param("start") Date startTime,
                                                       @Param("end") Date endTime);

    /**
     * 根据用户ID查询该用户所有订单
     * @param userId 用户ID
     * @return
     */
    List<OrderRecordJsonEntity> findOrderRecordByUserId (@Param("userId") String userId,
                                                         @Param("year") String year);

    /**
     * 查找用户订单总金额
     * @param userId 用户ID
     * @return
     */
    BigDecimal findOrderTotalPriceByUserId (@Param("userId") String userId,
                                            @Param("year") String year);

    /**
     * 查询用户最高金额的订单
     * @param userId 用户ID
     * @param year 年份
     * @return
     */
    OrderRecordJsonEntity findMaxOrderPriceByUserId (@Param("userId") String userId,
                                                     @Param("year") String year);

}
