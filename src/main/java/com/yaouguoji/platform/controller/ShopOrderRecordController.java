package com.yaouguoji.platform.controller;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.constant.ShopOrderRankType;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.ShopInfoService;
import com.yaouguoji.platform.vo.ShopOrderRankVO;
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
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private ShopInfoService shopInfoService;

    @GetMapping("/order/shop/{shopId}")
    public CommonResult shopOrder(@PathVariable("shopId") int shopId, String start, String end) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            if (shopId <= 0 || startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByShopId(shopId);
            if (shopInfoDTO == null) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<OrderRecordDTO> ordersByShopId = orderRecordService.findOrdersByShopId(shopId, startTime, endTime);
            Map<String, Object> data = new HashMap<>();
            data.put("shopInfo", shopInfoDTO);
            data.put("orderList", ordersByShopId);
            return CommonResult.success(data);
        } catch (Exception e) {
            LOGGER.error("解析时间异常：[{}]", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/order/shop/rank")
    public CommonResult shopOrderRankTop(int limit, String start, String end, int type) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            if (limit <= 0 || startTime.after(endTime)
                    || (type != ShopOrderRankType.ORDER_NUM_COUNT && type != ShopOrderRankType.ORDER_PRICE_COUNT)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            Map<Integer, Object> shopIds2ResultMap = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, type);
            if (CollectionUtils.isEmpty(shopIds2ResultMap)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ShopInfoDTO> shopInfoDTOs = shopInfoService.batchFindByShopIdList(new ArrayList<>(shopIds2ResultMap.keySet()));
            List<ShopOrderRankVO> resultList = Lists.newArrayList();
            shopInfoDTOs.forEach(shopInfoDTO -> {
                ShopOrderRankVO shopOrderRankVO = new ShopOrderRankVO();
                shopOrderRankVO.setShopInfoDTO(shopInfoDTO);
                shopOrderRankVO.setData(shopIds2ResultMap.get(shopInfoDTO.getShopId()));
                resultList.add(shopOrderRankVO);
            });
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("解析时间异常: [{}]", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }
}
