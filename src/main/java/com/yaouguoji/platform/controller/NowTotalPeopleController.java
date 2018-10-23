package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.NowTotalPeopleService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class NowTotalPeopleController {
    @Resource
    NowTotalPeopleService nowTotalPeopleService;

    @GetMapping(value = "/total/people")
    public CommonResult nowPeople() {
        Map<String, Integer> nowPeople = nowTotalPeopleService.todayTotalPeople();
        if (CollectionUtils.isEmpty(nowPeople)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(nowPeople);
    }
}
