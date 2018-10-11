package com.yaouguoji.platform.service;

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
    List<ShopCountDTO> goodsSalesCount(int shopId, Date startTime, Date endTime);
    /**
     * 下单量查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> goodsPlayOrdersCount(int shopId, Date startTime, Date endTime);
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
    List<ShopCountDTO> timeSectionSalesAndVolumeCount(int shopId, Date startTime, Date endTime);
    /**
     * 日成交量走势
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> everydayVolumeCount(int shopId, Date startTime, Date endTime);
    /**
     * 商品类别销售排名
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopCountDTO> shopGoodsSellTypeCount(int shopId,Date startTime,Date endTime);

}
