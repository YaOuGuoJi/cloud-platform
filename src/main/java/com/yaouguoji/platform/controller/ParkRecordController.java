package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.ParkRecordDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.ParkRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ParkRecord")
public class ParkRecordController {
    @Resource
    ParkRecordService parkRecordService;
    /**
     * 根据查停车记录
     */
    @GetMapping(value = "/selectParkRecordById/{id}")
    public CommonResult selectParkRecordById(@PathVariable("id") Integer id){
        ParkRecordDTO parkRecordDTO = parkRecordService.selectParkRecordDROI(id);
        if (parkRecordDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(parkRecordDTO);
    }
    /**
     * 根据车牌查停车记录
     */
    @GetMapping(value = "/selectParkRecordByLiense/{license}")
    public CommonResult selectParkRecordByLiense(@PathVariable("license") String license){
        List<ParkRecordDTO> parkRecordDTOS =parkRecordService.selectParkRecordDTOC(license);
        if (parkRecordDTOS == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(parkRecordDTOS);
    }
    /**
     * 查出所有的停车记录
     */
    @GetMapping(value = "/selectParkRecordAll")
    public CommonResult selectParkRecordAll(){
        List<ParkRecordDTO> parkRecordDTOS =parkRecordService.selectParkRecordDTOA();
        if (parkRecordDTOS == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(parkRecordDTOS);
    }

    /**
     * 查出停车现在停车总数
     */
    @GetMapping(value = "/selectParkRecordCount")
    public CommonResult selectParkRecordCount(){
        Integer count = parkRecordService.selectParkRecordDTOCout();
        if (count == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(count);
    }

    /**
     * 增加停车记录
     * @param id
     * @param license
     * @param activeType
     * @return
     */
    @PostMapping("/addParkRecord")
    public CommonResult addParkRecord(@RequestParam("id")Integer id,
                                      @RequestParam("license")String license,
                                      @RequestParam("activeType")Integer activeType){
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        parkRecordDTO.setId(id);
        parkRecordDTO.setLicense(license);
        parkRecordDTO.setActiveType(activeType);
        if (parkRecordDTO == null ){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int data = parkRecordService.addParkRecordDTO(parkRecordDTO);
        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }


}
