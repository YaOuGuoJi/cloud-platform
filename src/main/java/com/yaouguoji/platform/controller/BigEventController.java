package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.BigEventDTO;
import com.xianbester.api.service.BigEventService;
import com.xianbester.api.service.CameraRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangqiang
 * @date 2019-01-19
 */
@RestController
public class BigEventController {

    private static final int ONE_MONTH = 30;
    private static final int TODAY = 1;

    @Reference
    private BigEventService bigEventService;

    @Reference
    private CameraRecordService cameraRecordService;

    @ModelAttribute
    public BigEventDTO get(Integer eventId) {
        if (eventId <= 0) {
            return null;
        }
        BigEventDTO bigEvent = bigEventService.getBigEvent(eventId);
        if (bigEvent == null) {
            return null;
        }
        return bigEvent;
    }

    /**
     * 查询今日活动参加人数
     *
     * @param bigEventDTO
     * @return
     */
    @GetMapping("/event/todayNumberOfPeople")
    public CommonResult numberOfParticipantsInTodayIsEvent(BigEventDTO bigEventDTO) {
        if (bigEventDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Integer areaId = bigEventDTO.getAreaId();
        Integer result = cameraRecordService.queryParticipantByTime(areaId, TODAY);
        Map<String, Integer> map = Maps.newHashMap();
        map.put("todayEventNum", result == null ? 0 : result);
        return CommonResult.success(map);
    }

    /**
     * 查询一月内参与活动人数(线上，线下)
     *
     * @param bigEventDTO
     * @return
     */
    @GetMapping("/event/monthNumberOfEvent")
    public CommonResult numberOfParticipantsInMonthIsEvent(BigEventDTO bigEventDTO) {
        if (bigEventDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Integer offlinePeopleNum = bigEventService.getOfflinePeopleNum(bigEventDTO.getId());
        Integer onlineResult = bigEventDTO.getEventApplyNum();
        Map<String, Integer> map = Maps.newHashMap();
        map.put("online", onlineResult == null ? 0 : onlineResult);
        map.put("offline", offlinePeopleNum == null ? 0 : offlinePeopleNum);
        return CommonResult.success(map);
    }

}
