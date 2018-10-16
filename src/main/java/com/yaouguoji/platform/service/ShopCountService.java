package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.GoodsSellTypeDTO;
import com.yaouguoji.platform.dto.OrderNumAndAmountDTO;
import com.yaouguoji.platform.dto.SalesDTO;
import com.yaouguoji.platform.dto.ShopCountDTO;

import java.util.Date;
import java.util.List;

public interface ShopCountService {
    /**
     * 销售额查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SalesDTO> SalesAndOrdrsCount(int shopId, Date startTime, Date endTime);
    /**
     * 下单量查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SalesDTO> playOrdersAndTotalCount(int shopId, Date startTime, Date endTime);

    /**
     * 某段时间，每天每段时间销量和交易额统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderNumAndAmountDTO> timeQuantumSalesAndOrderCount(int shopId, Date startTime, Date endTime);
    /**
     * 日或月交易额或下单量
     * @param shopId
     * @param year
     * @param month
     * @return
     */
    List<OrderNumAndAmountDTO> orderNumAndAmount(int shopId, String year, String month);
    /**
     * 商品类别销售排名
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<GoodsSellTypeDTO> goodsSellTypeCount(int shopId, Date startTime, Date endTime);

}
