package com.yaouguoji.platform.controller;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.CameraDTO;
import com.yaouguoji.platform.dto.CameraRecordDTO;
import com.yaouguoji.platform.enums.HttpStatus;

import com.yaouguoji.platform.service.AreaService;
import com.yaouguoji.platform.service.CameraRecordService;
import com.yaouguoji.platform.service.CameraService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/camera")
public class AreaController {

    @Resource
    private AreaService areaService;
    @Resource
    private CameraService cameraService;
    @Resource
    private CameraRecordService cameraRecordService;

    /**
     * 根据id查询分区信息
     * @param areaId
     * @return
     */
    @GetMapping(value = "/selectArea/{id}")
    public CommonResult selectByPrimaryKey(@PathVariable("id") Integer areaId){
        AreaDTO areaDTO = areaService.selectByPrimaryKey(areaId);
        if (areaDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }

        return CommonResult.success(areaDTO);
    }

    /**
     * 添加分区信息
     * @param areaId
     * @param aName
     * @param aSort
     * @return
     */
    @RequestMapping(value = "/insertArea",method = RequestMethod.POST)
    public CommonResult insertAreaInfo( @RequestParam("areaId") Integer areaId,
                                        @RequestParam("aName") String aName,
                                        @RequestParam("aSort") String aSort
                                        ){

        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setAreaId(areaId);
        areaDTO.setAName(aName);
        areaDTO.setASort(aSort);

        if (areaDTO == null ){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        int data = areaService.insert(areaDTO);

        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);

    }

    /**
     * 查询分区人数
     * @return
     */
    @GetMapping("/selectAreaPeopleNumber")
    public CommonResult findAllAreaCameraInfo(){

        List<AreaDTO> areaDTOS = areaService.selectAll();

        if (CollectionUtils.isEmpty(areaDTOS)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        List<CameraDTO> cameraDTOS = cameraService.selectByCameraIds();
        if (CollectionUtils.isEmpty(cameraDTOS)){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        Map<Integer, Integer> cameraId2AreaIdMap = Maps.newHashMap();
        for (CameraDTO cameraDTO : cameraDTOS){
            cameraId2AreaIdMap.put(cameraDTO.getCameraId(), cameraDTO.getAreaId());
        }
        List<CameraRecordDTO> cameraRecordDTOS =
                cameraRecordService.batchSelectAllRecords(new ArrayList<>(cameraId2AreaIdMap.keySet()));
        if (CollectionUtils.isEmpty(cameraRecordDTOS)){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        Map<Integer, Integer> areaId2PeopleNumMap = Maps.newHashMap();
        cameraRecordDTOS.forEach(cameraRecordDTO -> {
            int areaId = cameraId2AreaIdMap.get(cameraRecordDTO.getCameraId());

            if (areaId2PeopleNumMap.containsKey(areaId)) {
                areaId2PeopleNumMap.replace(areaId, areaId2PeopleNumMap.get(areaId) + cameraRecordDTO.getCrNumber());
            } else {
                areaId2PeopleNumMap.put(areaId, cameraRecordDTO.getCrNumber());
            }
        });

        areaDTOS.forEach(areaDTO -> {
            areaDTO.setNumber(areaId2PeopleNumMap.get(areaDTO.getAreaId()));
        });
        return CommonResult.success(areaDTOS);

    }
}
