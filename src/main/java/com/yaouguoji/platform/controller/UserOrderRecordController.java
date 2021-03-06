package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.OrderRecordDTO;
import com.xianbester.api.dto.OrderRecordJsonDTO;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.service.OrderRecordService;
import com.xianbester.api.service.UserInfoService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.enums.HttpStatus;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author liuwen
 * @date 2018/9/10
 */
@RestController
public class UserOrderRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderRecordController.class);

    @Reference
    private OrderRecordService orderRecordService;

    @Reference
    private UserInfoService userInfoService;

    @GetMapping("/user/order/page")
    public CommonResult userOrderRecordPage(int userId, int pageNum, int pageSize, String start, String end) {
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            PageInfo<OrderRecordDTO> orderPageInfo =
                    orderRecordService.pageFindOrderRecordByUserId(userId, pageNum, pageSize, startTime, endTime);
            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功！")
                    .data("userInfo", userInfoDTO)
                    .data("orderPageInfo", orderPageInfo)
                    .build();
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

    }

    /**
     * 生成用户年度报告
     *
     * @param userId 用户ID
     * @param year   年份
     * @return
     */
        @GetMapping("/user/order/report")
    public CommonResult userReport(String userId, String year, @RequestParam(required = false, defaultValue = "") String month) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(year)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO info;
        try {
            info = userInfoService.findUserInfoByUserId(Integer.parseInt(userId));
            if (info == null) {
                return CommonResult.fail(HttpStatus.NOT_FOUND.value, "没有此用户");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("参数异常", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String yearRegex = "20[1-5][0-9]";
        if (!Pattern.matches(yearRegex, year)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String monthRegex = "^0?[1-9]$|^1[0-2]$|^$";
        if (!Pattern.matches(monthRegex, month)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int totalUserNum = userInfoService.findTotalUserNum();
        List<OrderRecordJsonDTO> list = orderRecordService.findOrderRecordByUserId(userId, year, month);
        if (CollectionUtils.isEmpty(list)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        BigDecimal totalPrice = new BigDecimal("0.00");
        Map<String, CountPay> reportMap = Maps.newHashMap();
        OrderRecordJsonDTO maxPriceOrder = list.get(0);
        Map<String, Object> userReportMap = Maps.newHashMap();
        if (!StringUtils.isBlank(month)) {
            for (OrderRecordJsonDTO o : list) {
                totalPrice = totalPrice.add(o.getPrice());
                if (o.getPrice().compareTo(maxPriceOrder.getPrice()) > 0) {
                    maxPriceOrder = o;
                }
                int dayOfMonth = new DateTime(o.getAddTime()).getDayOfMonth();
                countPayByMonthOrDay(reportMap, o, dayOfMonth);
            }
            userReportMap.put("dayReport", reportMap);
        } else {
            for (OrderRecordJsonDTO o : list) {
                totalPrice = totalPrice.add(o.getPrice());
                if (o.getPrice().compareTo(maxPriceOrder.getPrice()) > 0) {
                    maxPriceOrder = o;
                }
                int monthOfYear = new DateTime(o.getAddTime()).getMonthOfYear();
                countPayByMonthOrDay(reportMap, o, monthOfYear);
            }
            SpecialOrder firstOrder = new SpecialOrder(list.get(0));
            userReportMap.put("monthReport", reportMap);
            userReportMap.put("firstOrder", firstOrder);
        }
        Integer orderNumber = list.size();
        Map<String, CountPay> payTypeMap = Maps.newHashMap();
        SpecialOrder maxPriceOrderFinal = new SpecialOrder(maxPriceOrder);
        Map<String, List<OrderRecordJsonDTO>> payTypeGroupingMap = list.stream().collect(Collectors.groupingBy(OrderRecordJsonDTO::getPayType));
        for (Map.Entry<String, List<OrderRecordJsonDTO>> entry : payTypeGroupingMap.entrySet()) {
            BigDecimal price = new BigDecimal("0.00");
            List<OrderRecordJsonDTO> value = entry.getValue();
            for (OrderRecordJsonDTO o : value) {
                price = price.add(o.getPrice());
            }
            CountPay countPay = new CountPay(value.size(), price);
            payTypeMap.put(entry.getKey(), countPay);
        }
        int beyondMe = orderRecordService.findUsersWhoAreLargeThanMySpending(totalPrice, year, month);
        BigDecimal beyondUserNum = new BigDecimal(totalUserNum - beyondMe);
        BigDecimal result = beyondUserNum.divide(new BigDecimal(totalUserNum), 2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal("100.00"));
        userReportMap.put("beyondPercent", result);
        userReportMap.put("rank", beyondMe);
        userReportMap.put("orderNumber", orderNumber);
        userReportMap.put("totalPrice", totalPrice);
        userReportMap.put("maxPriceOrder", maxPriceOrderFinal);
        userReportMap.put("payType", payTypeMap);
        userReportMap.put("userInfo", info);
        return CommonResult.success(userReportMap);
    }

    /**
     *统计用户年度或者月度支付次数和总金额
     * @param reportMap
     * @param o
     * @param dayOrMonth
     */
    private void countPayByMonthOrDay(Map<String, CountPay> reportMap, OrderRecordJsonDTO o, int dayOrMonth) {
        if (reportMap.containsKey(dayOrMonth + "")) {
            CountPay countPay = reportMap.get(dayOrMonth + "");
            countPay.plusPayTime();
            countPay.setPayPrice(countPay.getPayPrice().add(o.getPrice()));
        } else {
            CountPay countPay = new CountPay(1, o.getPrice() == null ? new BigDecimal("0.00") : o.getPrice());
            reportMap.put(dayOrMonth + "", countPay);
        }
    }

    /**
     * 统计支付的次数和合计金额
     */
    @Data
    private class CountPay {
        private int payTimes;
        private BigDecimal payPrice;

        CountPay(int payTimes, BigDecimal payPrice) {
            this.payTimes = payTimes;
            this.payPrice = payPrice;
        }

        void plusPayTime() {
            payTimes++;
        }
    }

    /**
     * 特殊订单（例如第一次下单或者全年最高订单)
     */
    @Data
    private class SpecialOrder {
        private String day;
        private String shopName;
        private String productList;
        private BigDecimal orderPrice;

        SpecialOrder(OrderRecordJsonDTO o) {
            this.day = new SimpleDateFormat("yyyy年MM月dd日").format(o.getAddTime());
            this.shopName = o.getShopName();
            this.orderPrice = o.getPrice();
            this.productList = o.getProductList();
        }
    }

}
