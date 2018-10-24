package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.TodayPriceAndFrequencyService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TodayPriceAndFrequencyController {
    @Resource
    private TodayPriceAndFrequencyService todayPriceAndFrequencyService;

    @GetMapping(value = "/total/priceAndFrequency")
    public CommonResult todayPriceAndFrequency() {
        Map<String, BigDecimal> todayPriceAndFrequency = todayPriceAndFrequencyService.todayPriceAndFrequency();
        if (CollectionUtils.isEmpty(todayPriceAndFrequency)) {
            return new CommonResultBuilder().code(200)
                    .message("无数据")
                    .data("price",0)
                    .data("frequency",0)
                    .build();
        }
        return CommonResult.success(todayPriceAndFrequency);
    }

}
