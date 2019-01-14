package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
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
            Map<String, BigDecimal> todayPriceAndFrequency = orderRecordService.todayPriceAndFrequency(startTime, endTime);
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

    /**
     * 7日或30日订单业态分布
     *
     * @return
     */
    @GetMapping("/order/type/count")
    public CommonResult selectTypeCount(){
        Map<String,Map<Integer,Object>> resultMap = Maps.newHashMap();
        Map<Integer,Object> sevenDaysCountMap = orderRecordService.selectTypeCount(MonthConstant.SEVEN_DAYS);
        if (CollectionUtils.isEmpty(sevenDaysCountMap)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        resultMap.put("sevenDay",sevenDaysCountMap);
        Map<Integer, Object> oneMonthCountMap = orderRecordService.selectTypeCount(MonthConstant.THIRTY_DAYS);
        if (CollectionUtils.isEmpty(oneMonthCountMap)){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        resultMap.put("oneMonth",oneMonthCountMap);
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

}
