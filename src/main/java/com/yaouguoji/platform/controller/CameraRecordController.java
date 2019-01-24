package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.TownCountDTO;
import com.xianbester.api.service.CameraRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, Integer> oneMonthTotalVisitors = cameraRecordService.queryVisitorsByTime(ONE_MONTH);
        if (oneMonthTotalVisitors.isEmpty()) {
            return CommonResult.fail(404, "无数据");
        }
        return CommonResult.success(oneMonthTotalVisitors);
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

    /**
     * 当前人数分布
     *
     * @return
     */
    @GetMapping("/now/peopleSpread")
    public CommonResult queryAreaPeopleNum() {
        List<TownCountDTO> townCountDTOs = cameraRecordService.locationPeopleCount(new DateTime(new Date()).minusMonths(10).toDate(), new Date());
        if (CollectionUtils.isEmpty(townCountDTOs)) {
            return CommonResult.fail(404, "暂时没有数据");
        }
        Integer peopleNum = townCountDTOs.stream().collect(Collectors.summingInt(TownCountDTO::getCountNum));
        CommonResultBuilder result = new CommonResultBuilder().code(200).data("peopleNum", peopleNum);
        Map<Integer, Integer> buildingPeople = townCountDTOs.stream().collect(Collectors.groupingBy(TownCountDTO::getBuilding, Collectors.summingInt(TownCountDTO::getCountNum)));
        Map<String, Integer> areaPeople = townCountDTOs.stream().collect(Collectors.groupingBy(TownCountDTO::getAreaName, Collectors.summingInt(TownCountDTO::getCountNum)));
        result.data("areaPeople", areaPeople);
        for (Map.Entry<Integer, Integer> buildMap : buildingPeople.entrySet()) {
            Map<Object, Integer> oneBuildPeople = townCountDTOs.stream().filter(townOrder -> townOrder.getBuilding().equals(buildMap.getKey())).collect(Collectors.toMap(TownCountDTO::getFloors, TownCountDTO::getCountNum));
            oneBuildPeople.put("total", buildMap.getValue());
            result.data("building" + buildMap.getKey(), oneBuildPeople);
        }
        return result.build();
    }
}
