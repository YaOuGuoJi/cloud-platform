package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xianbester.api.dto.ParkRecordDTO;
import com.xianbester.api.service.ParkRecordService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.util.CarUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkRecordController {

    @Reference
    private ParkRecordService parkRecordService;

    /**
     * 根据id查停车记录
     *
     * @param id
     * @return
     */
    @GetMapping("/park/record/{id}")
    public CommonResult selectParkRecordById(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ParkRecordDTO parkRecordDTO = parkRecordService.selectParkRecordDROById(id);
        if (parkRecordDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(parkRecordDTO);
    }

    /**
     * 根据车牌查停车记录
     *
     * @param license
     * @return
     */
    @GetMapping("/park/record/license")
    public CommonResult selectParkRecordByLicense(String license) {
        if (!CarUtils.checkLicense(license)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<ParkRecordDTO> parkRecordDTOS = parkRecordService.selectParkRecordDTOByLicense(license);
        if (!CollectionUtils.isEmpty(parkRecordDTOS)) {
            return CommonResult.success(parkRecordDTOS);
        }
        return CommonResult.fail(HttpStatus.NOT_FOUND);
    }

    /**
     * 查出停车现在停车总数
     *
     * @return
     */
    @GetMapping("/park/record/number")
    public CommonResult selectNowCarNum() {
        int count = parkRecordService.selectNowCarNum();
        return CommonResult.success(count);
    }

    /**
     * 增加/更新停车记录
     *
     * @param license
     * @param activeType
     * @return
     */
    @PostMapping("/park/record")
    public CommonResult addParkRecord(String license, Integer activeType) {
        if (CarUtils.checkLicense(license) && (activeType == 0 || activeType == 1)) {
            ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
            parkRecordDTO.setLicense(license);
            parkRecordDTO.setActiveType(activeType);
            int data = parkRecordService.addParkRecordDTO(parkRecordDTO);
            return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
}

