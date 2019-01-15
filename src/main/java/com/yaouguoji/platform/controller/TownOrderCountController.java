package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.OrderRecordCountDTO;
import com.xianbester.api.dto.OrderRecordRequest;
import com.xianbester.api.service.OrderRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.enums.HttpStatus;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqiang
 * @date 2019-01-11
 */
@RestController
public class TownOrderCountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TownOrderCountController.class);

    @Reference
    private OrderRecordService orderRecordService;

    /**
     * 今日销售额与频率
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/total/priceAndFrequency")
    public CommonResult todayPriceAndFrequency(@RequestParam(required = false, defaultValue = "") String start, @RequestParam(required = false, defaultValue = "") String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (start.compareTo("") == 0) {
                Map<String, Integer> time = Maps.newHashMap();
                rebulidTime(time);
                start = time.get("year") + "-" + time.get("month") + "-" + time.get("day") + " 00:00:00";
            }
            if (end.compareTo("") == 0) {
                Map<String, Integer> time = Maps.newHashMap();
                rebulidTime(time);
                end = time.get("year") + "-" + time.get("month") + "-" + time.get("day") + " " + time.get("hour") + ":" + time.get("minute") + ":" + time.get("second");
            }
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "开始时间必须小于结束时间");
            }
            if (endTime.after(new Date())) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "结束时间必须小于当前时间");
            }
            OrderRecordCountDTO todayPriceAndFrequency = orderRecordService.townOrderRecordCount(createOrderRecordRequest(startTime, endTime));
            if (todayPriceAndFrequency == null) {
                return new CommonResultBuilder().code(200)
                        .message("无数据")
                        .data("price", 0)
                        .data("frequency", 0)
                        .build();
            }
            return CommonResult.success(todayPriceAndFrequency);
        } catch (ParseException e) {
            LOGGER.error("时间参数异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/town/orderRecordCount")
    public CommonResult townOrderRecordCount() {
//        Date now = new Date("2018/10/30");
        Date now = new Date();
        Date before24h = new DateTime(now).minusHours(24).toDate();
        Date before48h = new DateTime(now).minusHours(48).toDate();
        Date before7Days = new DateTime(now).minusDays(7).toDate();
        Date beforeOneMonth = new DateTime(now).minusMonths(1).toDate();
        OrderRecordCountDTO oneDayData = orderRecordService.townOrderRecordCount(createOrderRecordRequest(before24h, now));
        OrderRecordCountDTO twoDayData = orderRecordService.townOrderRecordCount(createOrderRecordRequest(before48h, before24h));
        OrderRecordCountDTO sevenDayData = orderRecordService.townOrderRecordCount(createOrderRecordRequest(before7Days, now));
        OrderRecordCountDTO oneMonthData = orderRecordService.townOrderRecordCount(createOrderRecordRequest(beforeOneMonth, now));
        Map<String, Map<String, BigDecimal>> orderCount = Maps.newHashMap();
        orderCount.put("frequency", createResult(twoDayData, oneDayData, "frequency"));
        orderCount.put("price", createResult(twoDayData, oneDayData, "price"));
        orderCount.put("peopleNum", createResult(twoDayData, oneDayData, "peopleNum"));
        orderCount.put("averagePrice", createResult(twoDayData, oneDayData, "averagePrice"));
        orderCount.put("sevenDayTotal", createTotal(sevenDayData));
        orderCount.put("oneMonthTotal", createTotal(oneMonthData));
        return CommonResult.success(orderCount);
    }

    private void rebulidTime(Map<String, Integer> time) {
        DateTime nowTime = new DateTime();
        time.put("year", nowTime.getYear());
        time.put("month", nowTime.getMonthOfYear());
        time.put("day", nowTime.getDayOfMonth());
        time.put("hour", nowTime.getHourOfDay());
        time.put("minute", nowTime.getMinuteOfHour());
        time.put("second", nowTime.getSecondOfMinute());
    }

    /**
     * 计算增长，生成新的map集合
     *
     * @param beforeData
     * @param afterData
     * @param field
     * @return
     */
    Map<String, BigDecimal> createResult(OrderRecordCountDTO beforeData, OrderRecordCountDTO afterData, String field) {
        Class dataClass = beforeData.getClass();
        Field fieldClass;
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        try {
            fieldClass = dataClass.getDeclaredField(field);
            fieldClass.setAccessible(true);
            BigDecimal beforeValue = (BigDecimal) fieldClass.get(beforeData);
            BigDecimal afterValue = (BigDecimal) fieldClass.get(afterData);
            if (afterData == null) {
                resultMap.put(field, BigDecimal.ZERO);
            } else {
                resultMap.put(field, afterValue);
            }
            if (beforeData.equals(BigDecimal.ZERO)) {
                if (afterData.equals(BigDecimal.ZERO)) {
                    resultMap.put("raise", BigDecimal.ZERO);
                }
                resultMap.put("raise", BigDecimal.valueOf(5));
                return resultMap;
            }
            resultMap.put("raise", afterValue.divide(beforeValue, 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LOGGER.error("反射获取字段类错误");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LOGGER.error("反射获取字段值错误");
        }
        return resultMap;
    }

    /**
     * 生成概况
     *
     * @param orderRecordCountDTO
     * @return
     */
    private Map<String, BigDecimal> createTotal(OrderRecordCountDTO orderRecordCountDTO) {
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        if (orderRecordCountDTO.getPrice() == null) {
            resultMap.put("price", BigDecimal.ZERO);
        } else {
            resultMap.put("price", orderRecordCountDTO.getPrice());
        }
        if (orderRecordCountDTO.getFrequency() == null) {
            resultMap.put("frequency", BigDecimal.ZERO);
        } else {
            resultMap.put("frequency", orderRecordCountDTO.getFrequency());
        }
        if (orderRecordCountDTO.getPeopleNum() == null) {
            resultMap.put("peopleNum", BigDecimal.ZERO);
        }
        resultMap.put("peopleNum", orderRecordCountDTO.getPeopleNum());
        return resultMap;
    }

    /**
     * 创建service层传入参数
     *
     * @param start
     * @param end
     * @return
     */
    private OrderRecordRequest createOrderRecordRequest(Date start, Date end) {
        OrderRecordRequest orderRecordRequest = new OrderRecordRequest();
        orderRecordRequest.setStartTime(start);
        orderRecordRequest.setEndTime(end);
        return orderRecordRequest;
    }
}
