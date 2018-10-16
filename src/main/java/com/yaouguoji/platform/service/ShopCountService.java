package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ShopCountDTO;

import java.util.Date;
import java.util.List;

public interface ShopCountService {

    /**
     * 商户已完成的订单统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> ordersFinished(int shopId, Date startTime, Date endTime);

    /**
     * 商户已取消的订单统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> ordersCanceled(int shopId, Date startTime, Date endTime);

    /**
     * 商户订单按小时统计
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> ordersByHours(int shopId, Date startTime, Date endTime);

    /**
     * 商户订单按照月份或天统计 month为空时按照month统计，month不为空时按天统计
     *
     * @param shopId
     * @param year
     * @param month
     * @return
     */
    List<ShopCountDTO> ordersByMonthOrDay(int shopId, int year, int month);

    /**
     * 按照商品类型统计订单
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> goodsSellTypeCount(int shopId, Date startTime, Date endTime);

}
