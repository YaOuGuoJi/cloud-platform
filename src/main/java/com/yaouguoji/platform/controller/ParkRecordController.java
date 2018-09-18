package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ParkRecordDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ParkRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/parkRecord")
public class ParkRecordController {
    @Resource
    private ParkRecordService parkRecordService;

    /**
     * 根据id查停车记录
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/selectParkRecordById/{id}")
    public CommonResult selectParkRecordById(@PathVariable("id") Integer id) {
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
    @GetMapping(value = "/selectParkRecordByLicense/{license}")
    public CommonResult selectParkRecordByLicense(@PathVariable("license") String license) {

        List<ParkRecordDTO> parkRecordDTOS = parkRecordService.selectParkRecordDTOByLicense(license);
        if (parkRecordDTOS != null) {
            return CommonResult.success(parkRecordDTOS);
        }
        return CommonResult.fail(HttpStatus.NOT_FOUND);
    }

    /**
     * 查出停车现在停车总数
     *
     * @return
     */
    @GetMapping(value = "/selectNowCarNum")
    public CommonResult selectNowCarNum() {
        Integer count = parkRecordService.selectNowCarNum();
        return CommonResult.success(count);
    }

    /**
     * 增加停车记录
     *
     * @param license
     * @param activeType
     * @return
     */
    @PostMapping("/addParkRecord")
    public CommonResult addParkRecord(String license, Integer activeType) {
        if ((license.length()==7||license.length()==8) && (activeType == 0 || activeType == 1)) {
            ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
            parkRecordDTO.setLicense(license);
            parkRecordDTO.setActiveType(activeType);
            int data = parkRecordService.addParkRecordDTO(parkRecordDTO);
            return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
}

