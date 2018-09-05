package com.yaouguoji.platform.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.constant.ShopOrderRankType;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.ShopInfoService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ShopOrderRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderRecordController.class);

    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private ShopInfoService shopInfoService;

    @GetMapping("/order/shop/{shopId}")
    public CommonResult shopOrder(@PathVariable("shopId") int shopId) {
        if (shopId <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByShopId(shopId);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<OrderRecordDTO> ordersByShopId =
                orderRecordService.findOrdersByShopId(shopId, new DateTime().minusMonths(1).toDate(), new Date());
        Map<String, Object> data = new HashMap<>();
        data.put("shopInfo", shopInfoDTO);
        data.put("orderList", ordersByShopId);
        return CommonResult.success(data);
    }

    @GetMapping("/order/shop/rank")
    public CommonResult shopOrderRankTop(int limit, String start, String end, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (limit <= 0 || startTime.after(endTime)
                    || (type != ShopOrderRankType.ORDER_NUM_COUNT && type != ShopOrderRankType.ORDER_PRICE_COUNT)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            Map<Integer, Object> shopIds2ResultMap = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, type);
            if (CollectionUtils.isEmpty(shopIds2ResultMap)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ShopInfoDTO> shopInfoDTOs = shopInfoService.batchFindByShopIdList(new ArrayList<>(shopIds2ResultMap.keySet()));
            List<Map<String, Object>> resultList = Lists.newArrayList();
            shopInfoDTOs.forEach(shopInfoDTO -> {
                Map<String, Object> dataMap = Maps.newHashMap();
                dataMap.put("shopInfo", shopInfoDTO);
                dataMap.put("count", shopIds2ResultMap.get(shopInfoDTO.getShopId()));
                resultList.add(dataMap);
            });
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("parse date error: [{}]", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }
}
