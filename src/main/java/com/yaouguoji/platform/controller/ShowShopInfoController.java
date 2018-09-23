package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.UserAgeAndSexSplitDTO;
import com.yaouguoji.platform.dto.UserFrequencyCountAndPriceCountDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.UserAgeAndSexSplitService;
import com.yaouguoji.platform.service.UserFrequencyCountAndPriceCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ShowShopInfoController {
    @Resource
    private UserFrequencyCountAndPriceCountService userFrequencyCountAndPriceCountService;
    @Resource
    private UserAgeAndSexSplitService userAgeAndSexSplitService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderRecordController.class);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @GetMapping(value = "shopManagementInfo")
    public CommonResult<Map<String, Object>> selectShopManagementInfo(Integer shopId, String start, String end) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            List<UserAgeAndSexSplitDTO> userAgeAndSexSplitDTOs = userAgeAndSexSplitService.selectAgeAndSexSplit(shopId, startTime, endTime);
            UserFrequencyCountAndPriceCountDTO userFrequencyCountAndPriceCountDTO = userFrequencyCountAndPriceCountService.selectUserFrequencyCountAndPriceCount(shopId, startTime, endTime);
            if (userAgeAndSexSplitDTOs != null && userFrequencyCountAndPriceCountDTO != null) {
                return new CommonResultBuilder()
                        .code(200)
                        .message("成功")
                        .data("userAgeAndSexSplit", userAgeAndSexSplitDTOs)
                        .data("userFrequencyCountAndPriceCount", userFrequencyCountAndPriceCountDTO)
                        .build();
            }
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            LOGGER.error("参数时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

    }
}