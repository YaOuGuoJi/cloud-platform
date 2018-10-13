package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.ShopCountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopCountService;
import org.apache.commons.lang3.StringUtils;
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
     * 商户销售信息查询
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/shop/count/")
    public CommonResult shopAllInfoCount(Integer shopId,String startTime,String endTime){
        if(shopId==null || StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try {
            Date start = smp.parse(startTime);
            Date end =  smp.parse(endTime);
            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功")
                    .data("营销额(成交量和成交额)",shopCountService.SalesAndOrdrsCount(shopId,start,end)==null ? "查询为空"
                            : shopCountService.SalesAndOrdrsCount(shopId,start,end))
                   // .data("商品成交量统计",shopCountService.goodsStrikeCount(shopId,start,end))
                    .data("营销额（下单量和交易额）",shopCountService.playOrdersAndTotalCount(shopId,start,end)==null ? "查询为空"
                            : shopCountService.playOrdersAndTotalCount(shopId,start,end))
                    .data("日成交量和交易额",shopCountService.timeQuantumSalesAndOrderCount(shopId,start,end)==null ? "查询为空"
                            : shopCountService.timeQuantumSalesAndOrderCount(shopId,start,end))
                    .data("一天个时间段的交易额和下单量",shopCountService.oneDaySalesAndOrdersCount(shopId,start,end)==null ? "查询为空"
                            : shopCountService.oneDaySalesAndOrdersCount(shopId,start,end))
                    .data("销售类别统计",shopCountService.goodsSellTypeCount(shopId,start,end)==null ? "查询为空"
                            :shopCountService.goodsSellTypeCount(shopId,start,end))
                    .build();

        } catch (ParseException e) {
            LOGGER.error("时间解析错误",e);
        }

        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
}
