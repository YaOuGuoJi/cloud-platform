package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xianbester.api.service.NowTotalPeopleService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NowTotalPeopleController {

    @Reference
    private NowTotalPeopleService nowTotalPeopleService;

    @GetMapping(value = "/total/people")
    public CommonResult nowPeople() {
        Map<String, Integer> nowPeople = nowTotalPeopleService.todayTotalPeople();
        if (CollectionUtils.isEmpty(nowPeople)) {
            return new CommonResultBuilder().code(200)
                    .message("无数据")
                    .data("totalPeople", 0)
                    .build();
        }
        return CommonResult.success(nowPeople);
    }
}
