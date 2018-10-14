package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.UserPriceCountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ShowUserInfoToShop;
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
    @Resource
    private ShowUserInfoToShop showUserInfoToShop;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowShopInfoController.class);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping(value = "shopManagementInfo")
    public CommonResult<Map<String, Object>> selectShopManagementInfo(Integer shopId, String start, String end) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            Map<String, Map<String, Integer>> userSexAndAges = showUserInfoToShop.selectAgeAndSexSplit(shopId, startTime, endTime);
            UserPriceCountDTO userPriceCountDTO = showUserInfoToShop.selectUserPriceCount(shopId, startTime, endTime);
            Map<String, Object> userFrequency = showUserInfoToShop.selectUserFrequencyCount(shopId, startTime, endTime);
            if (!CollectionUtils.isEmpty(userSexAndAges) && userPriceCountDTO != null && !CollectionUtils.isEmpty(userFrequency)) {
                return new CommonResultBuilder()
                        .code(200)
                        .message("成功")
                        .data("userAgeAndSexSplit", userSexAndAges)
                        .data("userPriceCount", userPriceCountDTO)
                        .data("userFrequency", userFrequency)
                        .build();
            }
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            LOGGER.error("参数时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

    }
}