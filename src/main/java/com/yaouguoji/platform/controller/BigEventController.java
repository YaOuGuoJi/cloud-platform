package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.xianbester.api.constant.OrderStatus;
import com.xianbester.api.dto.BigEventDTO;
import com.xianbester.api.dto.UserInfoDTO;
import com.xianbester.api.service.BigEventService;
import com.xianbester.api.service.CameraRecordService;
import com.xianbester.api.service.OrderRecordService;
import com.xianbester.api.service.UserInfoService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Reference
    private UserInfoService userInfoService;

    @Reference
    private OrderRecordService orderRecordService;

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
     * @return
     */
    @GetMapping("/event/todayNumberOfPeople")
    public CommonResult numberOfParticipantsInTodayIsEvent() {
        Map<String, Integer> map = Maps.newHashMap();
        List<BigEventDTO> notFinishedEvent = bigEventService.findNotFinishedEvent(BigEventDTO.NOT_FINISHED);
        if (CollectionUtils.isEmpty(notFinishedEvent)) {
            map.put("todayEventNum", 0);
        }
        int sum = 0;
        Date start = new DateTime().withTimeAtStartOfDay().toDate();
        Date end = new Date();
        for (BigEventDTO bigEventDTO : notFinishedEvent) {
            String location = bigEventDTO.getLocation();
            String[] locationIds = location.split(",");
            List<String> locationIdList = Stream.of(locationIds).collect(Collectors.toList());
            int num = cameraRecordService.queryParticipantByTime(locationIdList, start, end);
            sum += num;
        }
        map.put("todayEventNum", sum);
        return CommonResult.success(map);
    }

    /**
     * 查询30天内参与活动人数(线上，线下,)、参与活动的新用户
     *
     * @return
     */
    @GetMapping("/event/monthNumberOfEvent")
    public CommonResult numberOfParticipantsInMonthEvent() {
        Date start = new DateTime().minusDays(ONE_MONTH).toDate();
        Date end = new Date();
        int online = 0, offline = 0, newApply = 0;
        Map<String, Integer> map = Maps.newHashMap();
        List<BigEventDTO> eventListInMonth = bigEventService.findEventListInMonth(start, end);
        if (CollectionUtils.isEmpty(eventListInMonth)) {
            map.put("online", online);
            map.put("offline", offline);
            map.put("newApply", newApply);
            return CommonResult.success(map);
        }
        for (BigEventDTO bigEventDTO : eventListInMonth) {
            String locationIds = bigEventDTO.getLocation();
            List<String> locationIdList = Stream.of(locationIds.split(",")).collect(Collectors.toList());
            if (bigEventDTO.getBeginTime().before(start) && bigEventDTO.getEndTime().after(start)) {
                offline += cameraRecordService.queryParticipantByTime(locationIdList, start, bigEventDTO.getEndTime());
                online += bigEventDTO.getEventApplyNum() == null ? 0 : bigEventDTO.getEventApplyNum();
                newApply += bigEventDTO.getNewApply() == null ? 0 : bigEventDTO.getNewApply();
            }
            if (bigEventDTO.getBeginTime().before(end) && bigEventDTO.getEndTime().after(end)) {
                offline += cameraRecordService.queryParticipantByTime(locationIdList, bigEventDTO.getBeginTime(), end);
                online += bigEventDTO.getEventApplyNum() == null ? 0 : bigEventDTO.getEventApplyNum();
                newApply += bigEventDTO.getNewApply() == null ? 0 : bigEventDTO.getNewApply();
            }
            online += bigEventDTO.getEventApplyNum() == null ? 0 : bigEventDTO.getEventApplyNum();
            offline += bigEventDTO.getEventJoinNum() == null ? 0 : bigEventDTO.getEventJoinNum();
            newApply += bigEventDTO.getNewApply() == null ? 0 : bigEventDTO.getNewApply();
        }
        map.put("online", online);
        map.put("offline", offline);
        map.put("newApply", newApply);
        return CommonResult.success(map);
    }

    /**
     * 查询一月内活动总金额
     *
     * @return
     */
    @GetMapping("/event/amountInMonth")
    public CommonResult eventAmountInMonth() {
        Date start = new DateTime().minusDays(ONE_MONTH).toDate();
        Date end = new Date();
        List<BigEventDTO> eventListInMonth = bigEventService.findEventListInMonth(start, end);
        Map<String, BigDecimal> map = Maps.newHashMap();
        BigDecimal amount = new BigDecimal("0.00");
        if (CollectionUtils.isEmpty(eventListInMonth)) {
            map.put("amountInMonth", amount);
            return CommonResult.success(map);
        }
        for (BigEventDTO bigEventDTO : eventListInMonth) {
            String shopIds = bigEventDTO.getShopIdList();
            List<String> shopIdList = Stream.of(shopIds).collect(Collectors.toList());
            if (bigEventDTO.getBeginTime().before(start) && bigEventDTO.getEndTime().after(start)) {
                BigDecimal totalAmountInInterval =
                        orderRecordService.findAmountInMonthByShopIdList(shopIdList, start, bigEventDTO.getEndTime(), OrderStatus.COMPLETE);
                amount = amount.add(totalAmountInInterval == null ? new BigDecimal("0.00") : totalAmountInInterval);
            }
            if (bigEventDTO.getBeginTime().before(end) && bigEventDTO.getEndTime().after(end)) {
                BigDecimal totalAmountInInterval =
                        orderRecordService.findAmountInMonthByShopIdList(shopIdList, bigEventDTO.getBeginTime(), end, OrderStatus.COMPLETE);
                amount = amount.add(totalAmountInInterval == null ? new BigDecimal("0.00") : totalAmountInInterval);
            }
            BigDecimal totalAmountInMonth = orderRecordService.findAmountInMonthByShopIdList(shopIdList, start, end, OrderStatus.COMPLETE);
            amount = amount.add(totalAmountInMonth == null ? new BigDecimal("0.00") : totalAmountInMonth);
        }
        map.put("amountInMonth", amount);
        return CommonResult.success(map);
    }

    /**
     * 一月内发布的活动数
     *
     * @return
     */
    @GetMapping("/event/countEventMonth")
    public CommonResult numberOfEventInMonth() {
        Date start = new DateTime().minusDays(ONE_MONTH).toDate();
        Date end = new Date();
        Integer result = bigEventService.countEventInMonth(start, end);
        Map<String, Integer> map = Maps.newHashMap();
        map.put("totalInMonth", result);
        return CommonResult.success(map);
    }

    /**
     * 点击参加活动
     *
     * @param phoneNum
     * @param bigEventDTO
     * @return
     */
    @GetMapping("/event/join")
    public CommonResult join(String phoneNum, BigEventDTO bigEventDTO) {
        if (StringUtils.isEmpty(phoneNum) || bigEventDTO == null) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByPhoneNum(phoneNum);
        if (userInfoDTO == null) {
            Integer newApply = bigEventDTO.getNewApply() == null ? 0 : bigEventDTO.getNewApply() + 1;
            Integer applyNum = bigEventDTO.getEventApplyNum() == null ? 0 : bigEventDTO.getEventApplyNum() + 1;
            int updateNewApply = bigEventService.updateByUniqueField("NewApply", newApply, bigEventDTO.getId());
            int updateApply = bigEventService.updateByUniqueField("EventApplyNum", applyNum, bigEventDTO.getId());
            if (updateNewApply < 0 || updateApply < 0) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            return CommonResult.success();
        } else {
            String eventApplyNum = bigEventDTO.getEventApplyNum() + 1 + "";
            Integer result = bigEventService.updateByUniqueField("EventApplyNum", eventApplyNum, bigEventDTO.getId());
            if (result < 0) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            return CommonResult.success();
        }
    }

}
