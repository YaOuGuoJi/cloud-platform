package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.ShopCountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ShopCountController {
    @Resource
    private ShopCountService shopCountService;
    private static  final SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ShopCountController.class);
    }

    /**
     * 销售额统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/goodsSales")
    public CommonResult goodsSalesCount(Integer shopId, String startTime, String endTime) {
        if(shopId==null || startTime==null || endTime==null){
            CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try {
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);
            return CommonResult.success(shopCountService.goodsSalesCount(shopId, start, end));
        } catch (ParseException e) {
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    *//**
     * 下单量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/goodsPlayOrders")
    public CommonResult goodsPlayOrdersCount(Integer shopId, String startTime, String endTime) {
        if(shopId==null || startTime==null || endTime==null){
            CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try{
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);

            return CommonResult.success(shopCountService.goodsPlayOrdersCount(shopId, start, end));
        }catch (ParseException e){
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    *//**
     * 商品成交量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/goodsStrike")
    public CommonResult goodsStrikeCount(Integer shopId, String startTime, String endTime) {

        if(shopId==null || startTime==null || endTime==null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
            try{
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);

            return CommonResult.success(shopCountService.goodsStrikeCount(shopId, start, end));
        }catch (ParseException e){
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    *//**
     * 一天各时间段内交易额及交易订单统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/timeSectionSalesAndVolume")
    public CommonResult timeSectionSalesAndVolumeCount(Integer shopId, String startTime, String endTime){
        if(shopId==null || startTime==null || endTime ==null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try{
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);

            return CommonResult.success(shopCountService.timeSectionSalesAndVolumeCount(shopId, start, end));
        }catch (ParseException e){
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }


    *//**
     * 日成交量走势
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/everydayVolume")
    public CommonResult everydayVolumeCount(Integer shopId, String startTime, String endTime){
        if(shopId == null || startTime == null || endTime == null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try{
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);

            return CommonResult.success(shopCountService.everydayVolumeCount(shopId, start, end));
        }catch (ParseException e){
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    *//**
     * 商品日销量统计
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     *//*
    @GetMapping("/shop/count/shopGoodsSellType")
    public CommonResult shopGoodsSellTypeCount(Integer shopId,String startTime,String endTime){
        if(shopId ==null || startTime==null || endTime ==null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try{
            Date start = smp.parse(startTime);
            Date end = smp.parse(endTime);

            return CommonResult.success(shopCountService.shopGoodsSellTypeCount(shopId, start, end));
        }catch (ParseException e){
            LOGGER.error("解析时间出错",e);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }*/

    /**
     * 商户销售信息查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/shop/count/")
    public CommonResult shopAllInfoCount(Integer shopId,String startTime,String endTime){
        if(shopId==null || startTime==null || endTime == null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try {
            Date start = smp.parse(startTime);
            Date end =  smp.parse(endTime);
            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功")
                    .data("营销额",shopCountService.goodsSalesCount(shopId,start,end))
                    .data("商品成交量统计",shopCountService.goodsStrikeCount(shopId,start,end))
                    .data("下单量统计",shopCountService.goodsPlayOrdersCount(shopId,start,end))
                    .data("日成交量",shopCountService.everydayVolumeCount(shopId,start,end))
                    .data("商品销售类别",shopCountService.shopGoodsSellTypeCount(shopId,start,end))
                    .data("一天各时间段交易额",shopCountService.timeSectionSalesAndVolumeCount(shopId,start,end))
                    .build();

        } catch (ParseException e) {
            LOGGER.error("时间解析错误",e);
        }

        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
}
