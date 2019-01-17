package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xianbester.api.service.CameraRecordService;
import com.yaouguoji.platform.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lizhi
 * @date 2019-01-16
 */
@RestController
public class CameraRecordController {
    private static final int ONE_MONTH = 30;
    private static final int SEVEN_DAYS = 7;
    private static final int TODAY = 1;
    @Reference
    private CameraRecordService cameraRecordService;

    /**
     * 一个月访客概况
     *
     * @return
     */
    @GetMapping("/queryVisitors/queryOneMonthVisitors")
    public CommonResult queryOneMonthVisitors() {
        Map<String, Integer> OneMonthTotalVisitors = cameraRecordService.queryVisitorsByTime(ONE_MONTH);
        if (OneMonthTotalVisitors.isEmpty()) {
            return CommonResult.fail(404, "无数据");
        }
        return CommonResult.success(OneMonthTotalVisitors);
    }

    /**
     * 七日访客概况
     *
     * @return
     */
    @GetMapping("/queryVisitors/querySevenDaysVisitors")
    public CommonResult querySevenDaysVisitors() {
        Map<String, Integer> sevenDaysTotalVisitors = cameraRecordService.queryVisitorsByTime(SEVEN_DAYS);
        if (sevenDaysTotalVisitors.isEmpty()) {
            return CommonResult.fail(404, "无数据");
        }
        return CommonResult.success(sevenDaysTotalVisitors);
    }

    /**
     * 今日访客概况
     *
     * @return
     */
    @GetMapping("/queryVisitors/queryTodayVisitors")
    public CommonResult queryTodayVisitors() {
        Map<String, Integer> todayTotalVisitors = cameraRecordService.queryVisitorsByTime(TODAY);
        if (todayTotalVisitors.isEmpty()) {
            return CommonResult.fail(404, "无数据");
        }
        return CommonResult.success(todayTotalVisitors);
    }
}
