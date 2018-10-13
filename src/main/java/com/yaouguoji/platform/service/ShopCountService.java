package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.GoodsSellTypeDTO;
import com.yaouguoji.platform.dto.SalesAndPlayOrderDTO;
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
     * 成交量查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> goodsStrikeCount(int shopId, Date startTime, Date endTime);
    /**
     * 某段时间，每天每段时间销量和交易额统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SalesAndPlayOrderDTO> timeQuantumSalesAndOrderCount(int shopId, Date startTime, Date endTime);
    /**
     * 日成交量走势
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SalesAndPlayOrderDTO> oneDaySalesAndOrdersCount(int shopId, Date startTime, Date endTime);
    /**
     * 商品类别销售排名
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<GoodsSellTypeDTO> goodsSellTypeCount(int shopId, Date startTime, Date endTime);

}
