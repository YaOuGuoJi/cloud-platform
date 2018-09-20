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
    @GetMapping("/userReport")
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
        BigDecimal totalPrice = orderRecordService.findOrderTotalPriceByUserId(userId, year);
        String  firstDay = new SimpleDateFormat("yyyy年MM月dd日").format(orderRecordJsonDTO.getAddTime());
        Map<String ,Object> firstOrderMap = Maps.newHashMap();
        firstOrderMap.put("firstTIme", firstDay);
        firstOrderMap.put("firstShop", orderRecordJsonDTO.getShopName());
        firstOrderMap.put("firstProductList", orderRecordJsonDTO.getProductList());
        firstOrderMap.put("firstPrice", orderRecordJsonDTO.getPrice());
        OrderRecordJsonDTO maxPriceOrder = orderRecordService.findMaxOrderPriceByUserId(userId, year);
        Map<String , Object> maxPriceOrderMap = Maps.newHashMap();
        String maxDay = new SimpleDateFormat("yyyy年MM月dd日").format(maxPriceOrder.getAddTime());
        maxPriceOrderMap.put("maxPriceDay", maxDay);
        maxPriceOrderMap.put("maxShop", maxPriceOrder.getShopName());
        maxPriceOrderMap.put("maxProductList", maxPriceOrder.getProductList());
        maxPriceOrderMap.put("maxPrice", maxPriceOrder.getPrice());
        Map<String , Object> monthReportMap = Maps.newHashMap();
        BigDecimal[] monthPrice = new BigDecimal[12];
        int aliPayTimes = 0 , weChatPayTimes = 0, cashTimes = 0;
        BigDecimal aliPayPrice = null, weChatPayPrice = null, cashPrice = null;
        Map<String , Object> aliPayMap = Maps.newHashMap();
        Map<String , Object> weChatPayMap = Maps.newHashMap();
        Map<String , Object> cashMap = Maps.newHashMap();
        Map<String , Object> payTypeMap = Maps.newHashMap();
        for (OrderRecordJsonDTO o : list) {
            int month = new DateTime(o.getAddTime()).getMonthOfYear();
            BigDecimal bigDecimal = monthPrice[month - 1];
            if (bigDecimal == null) {
                bigDecimal = o.getPrice();
            }
            bigDecimal = bigDecimal.add(bigDecimal);
            monthReportMap.put(month + "",bigDecimal);
            if (o.getPayType().equals(PayTypeConstant.ALI_PAY)) {
                CountPayType countPayType = new CountPayType(aliPayTimes, aliPayPrice, o).invoke();
                aliPayMap.put("times", countPayType.getPayTimes());
                aliPayMap.put("price", countPayType.getPayPrice());
            }
            if (o.getPayType().equals(PayTypeConstant.WECHAT_PAY)) {
                CountPayType countPayType = new CountPayType(weChatPayTimes, weChatPayPrice, o).invoke();
                weChatPayMap.put("times", countPayType.getPayTimes());
                weChatPayMap.put("price", countPayType.getPayPrice());
            }
            if (o.getPayType().equals(PayTypeConstant.CASH)) {
                CountPayType countPayType = new CountPayType(cashTimes, cashPrice, o).invoke();
                cashMap.put("times", countPayType.getPayTimes());
                cashMap.put("price", countPayType.getPayPrice());
            }
        }
        userReportMap.put("orderNumber", orderNumber);
        userReportMap.put("totalPrice", totalPrice);
        userReportMap.put("firstOrder", firstOrderMap);
        userReportMap.put("maxPriceOrder", maxPriceOrderMap);
        userReportMap.put("monthReport", monthReportMap);
        payTypeMap.put("aliPay", aliPayMap);
        payTypeMap.put("weChatPay", weChatPayMap);
        payTypeMap.put("cashPayment", cashMap);
        userReportMap.put("payType", payTypeMap);
        return CommonResult.success(userReportMap);
    }

    /**
     * 统计支付类型的次数和合计金额
     */
    private class CountPayType {
        private int payTimes;
        private BigDecimal payPrice;
        private OrderRecordJsonDTO o;

        public CountPayType(int payTimes, BigDecimal payPrice, OrderRecordJsonDTO o) {
            this.payTimes = payTimes;
            this.payPrice = payPrice;
            this.o = o;
        }

        public int getPayTimes() {
            return payTimes;
        }

        public BigDecimal getPayPrice() {
            return payPrice;
        }

        public CountPayType invoke() {
            payTimes++;
            BigDecimal price = o.getPrice();
            if (payPrice == null) {
                payPrice = price;
            }
            payPrice = price.add(price);
            return this;
        }
    }
}
