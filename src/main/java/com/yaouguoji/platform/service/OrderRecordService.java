package com.yaouguoji.platform.service;

import com.github.pagehelper.PageInfo;
import com.yaouguoji.platform.dto.OrderRecordDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuwen
 */
public interface OrderRecordService {

    /**
     * 查询时间段内各个区域订单量
     *
     * @param startTime
     * @param endTime
     * @param type
     * @return
     * @see com.yaouguoji.platform.constant.OrderRankType
     */
    Map<Integer, Object> findAreaOrderNumber(Date startTime, Date endTime, int type);

    /**
     * 分页查询商户订单记录
     *
     * @param shopId
     * @param pageNum
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<OrderRecordDTO> pageFindOrderRecordByShopId(int shopId, int pageNum, int pageSize,
                                                         Date startTime, Date endTime);

    /**
     * 分页查询用户订单记录
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<OrderRecordDTO> pageFindOrderRecordByUserId(int userId, int pageNum, int pageSize,
                                                         Date startTime, Date endTime);

    /**
     * 查询商户订单排序
     *
     * @param limit
     * @param startTime
     * @param endTime
     * @param type
     * @return
     * @see com.yaouguoji.platform.constant.OrderRankType
     */
    Map<Integer, Object> findShopIdsRankByOrders(int limit, Date startTime, Date endTime, int type);

    /**
     * 查询单个订单详细信息
     *
     * @param orderId
     * @return
     */
    OrderRecordDTO findOrderDetailsByOrderId(int orderId);

    /**
     * 查询用户订单记录
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderRecordDTO> findOrdersByUserId(int userId, Date startTime, Date endTime);

    /**
     * 查询商户订单记录
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderRecordDTO> findOrdersByShopId(int shopId, Date startTime, Date endTime);

    /**
     * 插入订单信息
     *
     * @param orderRecordDTO
     * @return
     */
    int addOrderInfo(OrderRecordDTO orderRecordDTO);
}
