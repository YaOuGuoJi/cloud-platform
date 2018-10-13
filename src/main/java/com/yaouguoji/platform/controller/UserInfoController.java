package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.UserInfoService;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserInfoController {

    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/user/vip/{userId}")
    public CommonResult isVip(@PathVariable("userId") int userId) {
        boolean b = userInfoService.selectIsVip(userId);
        return CommonResult.success(b);
    }

    /**
     * 用户消费查询
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public CommonResult userConsumption(@PathVariable("userId") int userId){
        if(userId<=0){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<OrderRecordDTO> consumPionData = orderRecordService.findOrdersByUserId(userId, new DateTime().minusMonths(1).toDate(), new Date());
        if(consumPionData==null && consumPionData.size()==0){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        Map<String,Object> datas = new HashMap<>();
        datas.put("consumPionData",consumPionData);
        return CommonResult.success(datas);


    }
}
