package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.GoodsSellTypeDTO;
import com.yaouguoji.platform.dto.OrderNumAndAmountDTO;
import com.yaouguoji.platform.dto.SalesDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopCountService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class ShopCountController {
    @Resource
    private ShopCountService shopCountService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopCountController.class);

    /**
     * 商户销售信息查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/shop/count")
    public CommonResult shopAllInfoCount(Integer shopId,String startTime,String endTime){
        if(shopId==null || StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        try {
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
            Date start = smp.parse(startTime);
            Date end =  smp.parse(endTime);
            //线上线下销售额和订单量查询
            List<SalesDTO> salesDTOSList = shopCountService.SalesAndOrdrsCount(shopId, start, end);
            if(CollectionUtils.isEmpty(salesDTOSList)){
                return CommonResult.fail(   HttpStatus.NOT_FOUND);
            }
            Map<String, AmountAndNum> salesDTOSMap = salesDTOSList.stream().collect(Collectors.toMap(SalesDTO::getOrderChannel,
                    dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
            //线上线下下单量和交易额
            List<SalesDTO> salesDTOSList1 = shopCountService.playOrdersAndTotalCount(shopId, start, end);
            if(CollectionUtils.isEmpty(salesDTOSList1)){
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            Map<String, AmountAndNum> AmountAndNumMap = salesDTOSList1.stream().collect(Collectors.toMap(SalesDTO::getOrderChannel,
                    dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));

            //一天各个时间段销量统计
            List<OrderNumAndAmountDTO> orderNumAndAmountDTOList1 = shopCountService.timeQuantumSalesAndOrderCount(shopId, start, end);
            if(CollectionUtils.isEmpty(orderNumAndAmountDTOList1)){
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            Map<String, AmountAndNum> orderNumAndAmountDTOList1Map = orderNumAndAmountDTOList1.stream().collect(Collectors.toMap(OrderNumAndAmountDTO::getTime,
                    dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
            //商品销售类别统计
            List<GoodsSellTypeDTO> goodsSellTypeDTOSList = shopCountService.goodsSellTypeCount(shopId, start, end);
            if(CollectionUtils.isEmpty(goodsSellTypeDTOSList)){
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            Map<String, AmountAndNum> goodsSellTypeDTOSMap = goodsSellTypeDTOSList.stream().collect(Collectors.toMap(GoodsSellTypeDTO::getProductType,
                    dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));

            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功")
                    .data("实际营销额(成交量和成交额)",salesDTOSMap)
                    .data("营销额（下单量和交易额）",AmountAndNumMap)
                    .data("一天各个时间段交量和交易额",orderNumAndAmountDTOList1Map)
                    .data("销售类别统计",goodsSellTypeDTOSMap)
                    .build();

        } catch (ParseException e) {
            LOGGER.error("时间解析错误",e);
        }

        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    /**
     * 年或月销售额和订单数量统计
     * @param shopId
     * @param year
     * @param month
     * @return
     */
    @GetMapping("/shop/count/ordersAndAmount")
    public CommonResult orderNumAndAmount(Integer shopId, String year, String month) {
        if (shopId == null || StringUtils.isEmpty(year)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<OrderNumAndAmountDTO> dtoList = shopCountService.orderNumAndAmount(shopId, year, month);
        if(CollectionUtils.isEmpty(dtoList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<String, AmountAndNum> result = dtoList.stream().collect(Collectors.toMap(OrderNumAndAmountDTO::getTime,
                dto -> new AmountAndNum(dto.getOrderNumb(), dto.getAmount())));
        return new CommonResultBuilder().code(200).message("查询成功！")
                .data(month == null ? "byMonth" : "byDay", result).build();
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
