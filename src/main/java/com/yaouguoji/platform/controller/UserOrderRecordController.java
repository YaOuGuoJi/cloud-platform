package com.yaouguoji.platform.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.constant.PayTypeConstant;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.OrderRecordJsonDTO;
import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.UserInfoService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * @param userId 用户ID
     * @param year 年份
     * @return
     */
    @GetMapping("/order/user/report")
    public CommonResult userReport (String userId, String year) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(year)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<OrderRecordJsonDTO> list = orderRecordService.findOrderRecordByUserId(userId, year);
        Map<String , Object> userReportMap = Maps.newHashMap();
        if (list == null || list.size() == 0) {
            return CommonResult.fail("未发现订单记录");
        }
        OrderRecordJsonDTO orderRecordJsonDTO = list.get(0);
        Integer orderNumber = list.size();
        BigDecimal totalPrice = null;
        SpecialOrder firstOrder = new SpecialOrder(orderRecordJsonDTO);
        OrderRecordJsonDTO maxPriceOrder = null;
        Map<String , CountPay> monthReportMap = Maps.newHashMap();
        BigDecimal[] monthPrice = new BigDecimal[12];
        int[] monthTimes = new int[12];
        BigDecimal maxPrice = null;
        Map<String , CountPay> payTypeMap = Maps.newHashMap();
        for (OrderRecordJsonDTO o : list) {
            maxPriceOrder = o;
            if (totalPrice == null) {
                totalPrice = o.getPrice();
            }
            totalPrice = totalPrice.add(o.getPrice());
            if (maxPrice == null) {
                maxPrice = o.getPrice();
            }
            if (o.getPrice().compareTo(maxPrice) > 0) {
                maxPriceOrder = o;
            }
            int month = new DateTime(o.getAddTime()).getMonthOfYear();
            BigDecimal bigDecimal = monthPrice[month - 1];
            if (bigDecimal == null) {
                bigDecimal = o.getPrice();
            }
            bigDecimal = bigDecimal.add(bigDecimal);
            monthPrice[month - 1] = bigDecimal;
            monthTimes[month - 1]++;
        }
        for (int i = 0; i < monthPrice.length; i++) {
            CountPay monthCountPay = new CountPay(monthTimes[i], monthPrice[i] == null ? new BigDecimal("0.00") : monthPrice[i]);
            monthReportMap.put(i + 1 + "", monthCountPay);
        }
        SpecialOrder maxPriceOrderFinal = new SpecialOrder(maxPriceOrder);
        Map<String, List<OrderRecordJsonDTO>> payTypeGroupingMap = list.stream().collect(Collectors.groupingBy(OrderRecordJsonDTO :: getPayType));
        for (String key : payTypeGroupingMap.keySet()) {
            BigDecimal price = null;
            String payType = null;
            for (OrderRecordJsonDTO o : payTypeGroupingMap.get(key)) {
                if (price == null) {
                    price = o.getPrice();
                }
                price = price.add(o.getPrice());
                if (payType == null) {
                    payType = o.getPayType();
                }
            }
            CountPay countPay = new CountPay(payTypeGroupingMap.get(key).size(), price == null ? new BigDecimal("0.00") : price);
            payTypeMap.put(payType, countPay);
        }
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

        public CountPay(int payTimes, BigDecimal payPrice) {
            this.payTimes = payTimes;
            this.payPrice = payPrice;
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

        public SpecialOrder (OrderRecordJsonDTO o) {
            this.day = new SimpleDateFormat("yyyy年MM月dd日").format(o.getAddTime());
            this.shopName = o.getShopName();
            this.orderPrice = o.getPrice();
            this.productList = o.getProductList();
        }
    }

}
