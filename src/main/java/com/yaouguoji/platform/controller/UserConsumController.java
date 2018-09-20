package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.UserConsumptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserConsumController {
    @Resource
    private UserConsumptionService userConsumptionService;
    /**
     * 用户年消费统计
     */
    @GetMapping("/user/consum")
    public CommonResult recentYearConsum(Integer userId){
        if(userId==null){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        Map<String, BigDecimal> recentYearConsumMap = new HashMap<>();
        BigDecimal res = userConsumptionService.recentYearConsumption(userId);
        recentYearConsumMap.put("年度总消费",res);
        return CommonResult.success(recentYearConsumMap);
    }
    /**
     * 用户消费类别统计
     */
    @GetMapping("/user/consumType")
    public CommonResult comsumptionType(Integer userId){
        if(userId == null){
            return null;
        }

        return  null;
    }
}
