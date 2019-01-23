package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xianbester.api.dto.CountDTO;
import com.xianbester.api.dto.OrderVolumeDTO;
import com.xianbester.api.service.OrderVolumeService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author lizhi
 * @date 2019-01-23
 */
@RestController
public class OrderVolumeController {
    @Reference
    private OrderVolumeService orderVolumeService;

    @GetMapping(value = "/order/byArea")
    public CommonResult queryOrderByArea() {
        Date today = new DateTime().withTimeAtStartOfDay().toDate();
        Date now = new Date();
        List<OrderVolumeDTO> orderVolumeDTOList = orderVolumeService.queryOrderByArea(today, now);
        if (CollectionUtils.isEmpty(orderVolumeDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(orderVolumeDTOList);
    }

    @RequestMapping(value = "/order/byBuilding")
    public CommonResult queryOrderByBuilding() {
        Date today = new DateTime().withTimeAtStartOfDay().toDate();
        Date now = new Date();
        List<CountDTO> countDTOList = orderVolumeService.queryOrderByBuilding(today, now);
        if (CollectionUtils.isEmpty(countDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(countDTOList);
    }

    @RequestMapping(value = "/order/byFloors")
    public CommonResult queryOrderByFloors(int building) {
        Date today = new DateTime().withTimeAtStartOfDay().toDate();
        Date now = new Date();
        List<CountDTO> countDTOList = orderVolumeService.queryOrderByFloors(today, now, building);
        if (CollectionUtils.isEmpty(countDTOList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(countDTOList);
    }

}
