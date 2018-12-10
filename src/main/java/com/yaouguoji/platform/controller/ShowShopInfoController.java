package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.UserPriceCountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShopUserAnalysisService;
import com.yaouguoji.platform.util.ShopInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class ShowShopInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowShopInfoController.class);
    @Resource
    private ShopUserAnalysisService shopUserAnalysisService;

    @GetMapping(value = "/shop/consumer/analysis")
    public CommonResult<Map<String, Object>> selectShopManagementInfo(String start, String end) {
        int shopId = ShopInfoUtil.getShopId();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR.value, "开始时间必须小于结束时间");
            }
            Map<String, Map<String, Integer>> userSexAndAges = shopUserAnalysisService.selectAgeAndSexCount(shopId, startTime, endTime);
            if (CollectionUtils.isEmpty(userSexAndAges)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            UserPriceCountDTO userPriceCountDTO = shopUserAnalysisService.selectUserPriceCount(shopId, startTime, endTime);
            Map<String, Object> userFrequency = shopUserAnalysisService.selectUserFrequencyCount(shopId, startTime, endTime);
            return new CommonResultBuilder().code(200).message("成功")
                    .data("sexAndAge", userSexAndAges)
                    .data("price", userPriceCountDTO)
                    .data("frequency", userFrequency)
                    .build();
        } catch (ParseException e) {
            LOGGER.error("时间参数异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }
}
