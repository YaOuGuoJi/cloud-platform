package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.BigEventDTO;
import com.xianbester.api.service.BigEventService;
import com.xianbester.api.service.CameraRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 查询今日活动参加人数
     *
     * @param eventId
     * @return
     */
    @GetMapping("/event/todayNumberOfPeople")
    public CommonResult numberOfParticipantsInTodayIsEvent(Integer eventId) {
        if (eventId < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        BigEventDTO bigEventEntity = bigEventService.getBigEventEntity(eventId);
        if (bigEventEntity == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Integer areaId = bigEventEntity.getAreaId();
        Integer result = cameraRecordService.queryParticipantByTime(areaId, TODAY);
        Map<String, Integer> map = Maps.newHashMap();
        map.put("todayEventNum", result == null ? 0 : result);
        return CommonResult.success(map);
    }


}
