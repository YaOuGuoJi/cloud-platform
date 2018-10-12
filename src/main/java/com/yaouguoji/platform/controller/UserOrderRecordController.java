package com.yaouguoji.platform.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.OrderRecordJsonDTO;
import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.UserInfoService;
import com.yaouguoji.platform.vo.ObjectMapDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuwen
 * @date 2018/9/10
 */
@RestController
public class UserOrderRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderRecordController.class);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private OrderRecordService orderRecordService;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/order/user/page")
    public CommonResult userOrderRecordPage(int userId, int pageNum, int pageSize, String start, String end) {
        if (userId <= 0 || pageNum <= 0 || pageSize <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(userId);
        if (userInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
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
    @GetMapping("/order/user/report")
    public CommonResult userReport(String userId, String year) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(year)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO info = userInfoService.findUserInfoByUserId(Integer.parseInt(userId));
        if (info == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<OrderRecordJsonDTO> list = orderRecordService.findOrderRecordByUserId(userId, year);
        int totalUserNum = userInfoService.findTotalUserNum();
        if (CollectionUtils.isEmpty(list)) {
            Map<String, Object> noRecordUserReportMap = Maps.newHashMap();
            noRecordUserReportMap.put("rank", totalUserNum);
            noRecordUserReportMap.put("beyondPercent", 0);
            return CommonResult.success(noRecordUserReportMap);
        }
        Integer orderNumber = list.size();
        BigDecimal totalPrice = new BigDecimal("0.00");
        SpecialOrder firstOrder = new SpecialOrder(list.get(0));
        OrderRecordJsonDTO maxPriceOrder = list.get(0);
        Map<String, CountPay> monthReportMap = Maps.newHashMap();
        for (OrderRecordJsonDTO o : list) {
            totalPrice = totalPrice.add(o.getPrice());
            if (o.getPrice().compareTo(maxPriceOrder.getPrice()) > 0) {
                maxPriceOrder = o;
            }
            int month = new DateTime(o.getAddTime()).getMonthOfYear();
            if (monthReportMap.containsKey(month + "")) {
                CountPay countPay = monthReportMap.get(month + "");
                countPay.plusPayTime();
                countPay.setPayPrice(countPay.getPayPrice().add(o.getPrice()));
            } else {
                CountPay countPay = new CountPay(1, o.getPrice() == null ? new BigDecimal("0.00") : o.getPrice());
                monthReportMap.put(month + "", countPay);
            }
        }
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
        int beyondMe = orderRecordService.findUsersWhoAreLargeThanMySpending(totalPrice);
        BigDecimal beyondUserNum = new BigDecimal(totalUserNum - beyondMe);
        BigDecimal result = beyondUserNum.divide(new BigDecimal(totalUserNum), 2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal("100.00"));
        Map<String, Object> userReportMap = Maps.newHashMap();
        userReportMap.put("beyondPercent", result);
        userReportMap.put("rank", beyondMe);
        userReportMap.put("orderNumber", orderNumber);
        userReportMap.put("totalPrice", totalPrice);
        userReportMap.put("firstOrder", firstOrder);
        userReportMap.put("maxPriceOrder", maxPriceOrderFinal);
        userReportMap.put("monthReport", monthReportMap);
        userReportMap.put("payType", payTypeMap);
        return CommonResult.success(userReportMap);
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
