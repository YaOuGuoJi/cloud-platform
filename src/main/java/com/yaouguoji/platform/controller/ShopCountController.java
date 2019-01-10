package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xianbester.api.dto.ShopCountDTO;
import com.xianbester.api.service.ShopCountService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.util.ShopInfoUtil;
import lombok.Data;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ShopCountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopCountController.class);

    @Reference
    private ShopCountService shopCountService;

    /**
     * 商户销售信息查询
     *
     * @param year
     * @param month
     * @return
     */
    @GetMapping("/shop/count")
    public CommonResult shopAllInfoCount(int year,
                                         @RequestParam(required = false, defaultValue = "0") int month) {
        int shopId = ShopInfoUtil.getShopId();
        if (year <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try {
            Date start = buildStartTime(year, month);
            DateTime startTime = new DateTime(start);
            Date end = month == 0 ? startTime.plusYears(1).toDate() : startTime.plusMonths(1).toDate();

            Map<String, Object> result = new HashMap<>(8);
            // 统计已完成订单
            List<ShopCountDTO> finished = shopCountService.ordersFinished(shopId, start, end);
            if (!CollectionUtils.isEmpty(finished)) {
                Map<String, AmountAndNum> finishedMap = finished.stream().collect(Collectors.toMap(ShopCountDTO::getGroupName,
                        dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
                result.put("finished", finishedMap);
            }

            // 已取消订单统计
            List<ShopCountDTO> canceledCount = shopCountService.ordersCanceled(shopId, start, end);
            if (!CollectionUtils.isEmpty(canceledCount)) {
                Map<String, AmountAndNum> canceledMap = canceledCount.stream().collect(Collectors
                        .toMap(ShopCountDTO::getGroupName, dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
                result.put("canceled", canceledMap);
            }

            // 按小时统计
            List<ShopCountDTO> byHours = shopCountService.ordersByHours(shopId, start, end);
            if (!CollectionUtils.isEmpty(byHours)) {
                Map<String, AmountAndNum> byHourMap = byHours.stream().collect(Collectors.toMap(ShopCountDTO::getGroupName,
                        dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
                result.put("byHour", byHourMap);
            }

            // 按照商品类别统计
            List<ShopCountDTO> byProductType = shopCountService.goodsSellTypeCount(shopId, start, end);
            if (!CollectionUtils.isEmpty(byProductType)) {
                Map<String, AmountAndNum> byTypeMap = byProductType.stream().collect(Collectors.toMap(ShopCountDTO::getGroupName,
                        dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
                result.put("byProductType", byTypeMap);
            }

            // 按低一级时间维度统计
            List<ShopCountDTO> byLowerTime = shopCountService.ordersByMonthOrDay(shopId, year, month);
            if (!CollectionUtils.isEmpty(byLowerTime)) {
                Map<String, AmountAndNum> byTimeMap = byLowerTime.stream().collect(Collectors.toMap(ShopCountDTO::getGroupName,
                        dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
                result.put(month <= 0 ? "byMonth" : "byDay", byTimeMap);
            }
            return CommonResult.success(result);
        } catch (ParseException e) {
            LOGGER.error("时间解析错误", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    private Date buildStartTime(int year, int month) throws ParseException {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        return smp.parse(String.valueOf(year) + "-" + (month == 0 ? "01" : month) + "-01");
    }

    @Data
    class AmountAndNum {
        private int number;
        private BigDecimal price;

        AmountAndNum(int number, BigDecimal price) {
            this.number = number;
            this.price = price;
        }
    }
}
