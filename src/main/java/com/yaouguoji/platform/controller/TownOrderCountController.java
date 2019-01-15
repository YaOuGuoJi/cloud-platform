package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.OrderRecordCountDTO;
import com.xianbester.api.dto.OrderRecordRequest;
import com.xianbester.api.service.OrderRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.constant.MonthConstant;
import com.yaouguoji.platform.enums.HttpStatus;
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
     * 按时间段查询订单类型分布
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/total/orderTypeDistribution")
    public CommonResult orderTypeDistribution(String start, String end) {
        if (start == null && end == null) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "开始时间必须小于结束时间");
            }
            Map<String, Integer> stringIntegerMap = orderRecordService.orderTypeDistribution(startTime, endTime);
            if (CollectionUtils.isEmpty(stringIntegerMap)) {
                return CommonResult.fail(404, "该时间段无数据");
            }
            return CommonResult.success(stringIntegerMap);
        } catch (ParseException e) {
            LOGGER.error("时间参数异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

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
        orderCount.put("todayFrequency", createResult(BigDecimal.valueOf(twoDayData.getFrequency()), BigDecimal.valueOf(oneDayData.getFrequency()), "frequency"));
        orderCount.put("todayPrice", createResult(twoDayData.getPrice(), oneDayData.getPrice(), "price"));
        orderCount.put("todayPeopleNum", createResult(BigDecimal.valueOf(twoDayData.getPeopleNum()), BigDecimal.valueOf(oneDayData.getPeopleNum()), "peopleNum"));
        orderCount.put("todayAveragePrice", createResult(twoDayData.getAveragePrice(), oneDayData.getAveragePrice(), "averagePrice"));
        orderCount.put("sevenDayTotal", createTotal(sevenDayData));
        orderCount.put("oneMonthTotal", createTotal(oneMonthData));
        return CommonResult.success(orderCount);
    }

    /**
     * 7日或30日订单业态分布
     *
     * @return
     */
    @GetMapping("/order/type/count")
    public CommonResult selectTypeCount() {
        Map<String, Map<Integer, Object>> resultMap = Maps.newHashMap();
        Map<Integer, Object> sevenDaysCountMap = orderRecordService.selectTypeCount(MonthConstant.SEVEN_DAYS);
        if (CollectionUtils.isEmpty(sevenDaysCountMap)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        resultMap.put("sevenDay", sevenDaysCountMap);
        Map<Integer, Object> oneMonthCountMap = orderRecordService.selectTypeCount(MonthConstant.THIRTY_DAYS);
        if (CollectionUtils.isEmpty(oneMonthCountMap)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        resultMap.put("oneMonth", oneMonthCountMap);
        return CommonResult.success(resultMap);
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
     * @param beforeValue
     * @param afterValue
     * @return
     */
    Map<String, BigDecimal> createResult(BigDecimal beforeValue, BigDecimal afterValue, String name) {
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        if (afterValue == null) {
            resultMap.put(name, BigDecimal.ZERO);
        } else {
            resultMap.put(name, afterValue);
        }
        if (beforeValue == null || beforeValue.equals(BigDecimal.ZERO)) {
            if (resultMap.get(name).equals(BigDecimal.ZERO)) {
                resultMap.put("raise", BigDecimal.ZERO);
                return resultMap;
            }
            resultMap.put("raise", BigDecimal.valueOf(5));
            return resultMap;
        }
        resultMap.put("raise", resultMap.get(name).divide(beforeValue, 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1)));
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
            resultMap.put("frequency", BigDecimal.valueOf(orderRecordCountDTO.getFrequency()));
        }
        if (orderRecordCountDTO.getPeopleNum() == null) {
            resultMap.put("peopleNum", BigDecimal.ZERO);
        }
        resultMap.put("peopleNum", BigDecimal.valueOf(orderRecordCountDTO.getPeopleNum()));
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
