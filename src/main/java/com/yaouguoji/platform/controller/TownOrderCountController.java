package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.service.OrderRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
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
            Map<String, BigDecimal> todayPriceAndFrequency = orderRecordService.townOrderRecordCount(startTime, endTime);
            if (CollectionUtils.isEmpty(todayPriceAndFrequency)) {
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
        Date before30Days = new DateTime(now).minusDays(30).toDate();
        Map<String, BigDecimal> oneDayData = orderRecordService.townOrderRecordCount(before24h, now);
        Map<String, BigDecimal> twoDayData = orderRecordService.townOrderRecordCount(before48h, before24h);
        Map<String, BigDecimal> sevenDayData = orderRecordService.townOrderRecordCount(before7Days, now);
        Map<String, BigDecimal> oneMonthData = orderRecordService.townOrderRecordCount(before30Days, now);
        Map<String, Map<String, BigDecimal>> orderCount = Maps.newHashMap();

        orderCount.put("todayOrder", createResult(twoDayData, oneDayData, "frequency"));
        orderCount.put("todayPeople", createResult(twoDayData, oneDayData, "peopleNum"));
        orderCount.put("todayPrice", createResult(twoDayData, oneDayData, "price"));
        orderCount.put("todayCustomerPrice", createResult(createMedian(twoDayData), createMedian(oneDayData), "customerPrice"));
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
     * @param beforeMap 时间较早的集合
     * @param afterMap  时间较晚的集合
     * @param key       处理的数据键值
     * @return
     */
    private Map<String, BigDecimal> createResult(Map<String, BigDecimal> beforeMap, Map<String, BigDecimal> afterMap, String key) {
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        resultMap.put(key, afterMap.get(key));
        if (beforeMap.get(key) == null || beforeMap.get(key).equals(BigDecimal.valueOf(0))) {
            if (beforeMap.get(key) == null || beforeMap.get(key).equals(BigDecimal.valueOf(0))) {
                resultMap.put(key, BigDecimal.valueOf(0));
                resultMap.put("raise", BigDecimal.valueOf(0));
            } else {
                resultMap.put("raise", BigDecimal.valueOf(5));
            }
        } else {
            resultMap.put("raise", afterMap.get(key).divide(beforeMap.get(key), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1)));
        }
        return resultMap;
    }

    /**
     * 计算客单价（消费额/人数），生成map集合
     *
     * @param startMap
     * @return
     */
    private Map<String, BigDecimal> createMedian(Map<String, BigDecimal> startMap) {
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        if (startMap.get("peopleNum").equals(BigDecimal.valueOf(0)) || startMap.get("peopleNum") == null) {
            resultMap.put("customerPrice", BigDecimal.valueOf(0));
            return resultMap;
        }
        resultMap.put("customerPrice", startMap.get("price").divide(startMap.get("peopleNum"), 10, BigDecimal.ROUND_HALF_UP));
        return resultMap;
    }

    /**
     * 生成概况
     *
     * @param startMap
     * @return
     */
    private Map<String, BigDecimal> createTotal(Map<String, BigDecimal> startMap) {
        HashMap<String, BigDecimal> resultMap = Maps.newHashMap();
        if (startMap.get("price") == null || startMap.get("price").equals(BigDecimal.valueOf(0))) {
            resultMap.put("price", BigDecimal.valueOf(0));
        } else {
            resultMap.put("price", startMap.get("price"));
        }
        resultMap.put("frequency", startMap.get("frequency"));
        resultMap.put("peopleNum", startMap.get("peopleNum"));
        return resultMap;
    }
}
