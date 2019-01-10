package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xianbester.api.dto.*;
import com.xianbester.api.service.*;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AreaController {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Reference
    private AreaService areaService;
    @Reference
    private CameraService cameraService;
    @Reference
    private CameraRecordService cameraRecordService;
    @Reference
    private ShopInfoService shopInfoService;
    @Reference
    private OrderRecordService orderRecordService;

    /**
     * 根据id查询分区信息
     *
     * @param areaId
     * @return
     */
    @GetMapping(value = "/selectArea/{id}")
    public CommonResult selectByPrimaryKey(@PathVariable("id") Integer areaId) {
        AreaDTO areaDTO = areaService.selectByPrimaryKey(areaId);
        if (areaDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(areaDTO);
    }

    /**
     * 添加分区信息
     *
     * @param areaId
     * @param aName
     * @param aSort
     * @return
     */
    @PostMapping(value = "/insertArea")
    public CommonResult insertAreaInfo(@RequestParam("areaId") Integer areaId,
                                       @RequestParam("aName") String aName,
                                       @RequestParam("aSort") String aSort) {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setAreaId(areaId);
        areaDTO.setAName(aName);
        areaDTO.setASort(aSort);
        int data = areaService.insert(areaDTO);
        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    /**
     * 查询分区人数
     *
     * @return
     */
    @GetMapping("/selectAreaPeopleNumber")
    public CommonResult findAllAreaCameraInfo() {
        List<AreaDTO> areaDTOS = areaService.selectAll();
        if (CollectionUtils.isEmpty(areaDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<CameraDTO> cameraDTOS = cameraService.selectByCameraIds();
        if (CollectionUtils.isEmpty(cameraDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<Integer, Integer> cameraId2AreaIdMap
                = cameraDTOS.stream().collect(Collectors.toMap(CameraDTO::getCameraId, CameraDTO::getAreaId));
        List<CameraRecordDTO> cameraRecordDTOS
                = cameraRecordService.batchSelectAllRecords(new ArrayList<>(cameraId2AreaIdMap.keySet()));
        if (CollectionUtils.isEmpty(cameraRecordDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<Integer, AreaDTO> areaMap = areaDTOS.stream().collect(Collectors.toMap(AreaDTO::getAreaId, area -> area));
        Map<Integer, ObjectMapDTO<AreaDTO, Integer>> resultMap = Maps.newHashMap();
        cameraRecordDTOS.forEach(cameraRecordDTO -> {
            int areaId = cameraId2AreaIdMap.get(cameraRecordDTO.getCameraId());
            ObjectMapDTO<AreaDTO, Integer> objectMapDTO = resultMap.get(areaId);
            if (objectMapDTO == null) {
                resultMap.put(areaId, new ObjectMapDTO<>(areaMap.get(areaId), cameraRecordDTO.getCrNumber()));
            } else {
                objectMapDTO.setNumber(objectMapDTO.getNumber() + cameraRecordDTO.getCrNumber());
            }
        });
        return CommonResult.success(resultMap.values());
    }

    /**
     * 查询一个分区内前几名商家的销售额、销售量排名
     *
     * @param limit
     * @param areaId
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/shop/area/rank")
    public CommonResult areaShopRank1(Integer limit, Integer areaId, String start, String end) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            OrderRecordRequest request = new OrderRecordRequest();
            request.setLimit(limit);
            request.setId(areaId);
            request.setStartTime(startTime);
            request.setEndTime(endTime);
            request.setType(1);
            List<ObjectMapDTO<ShopInfoDTO, Object>> orderCount = buildAreaShopList(request);
            request.setType(2);
            List<ObjectMapDTO<ShopInfoDTO, Object>> orderPrice = buildAreaShopList(request);
            return new CommonResultBuilder()
                    .code(200).message("查询成功")
                    .data("orderCount", orderCount)
                    .data("orderPrice", orderPrice)
                    .build();
        } catch (ParseException e) {
            LOGGER.info("时间解析异常", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    /**
     * 进行查询，并转换成需要的格式
     *
     * @param request
     * @return
     */
    private List<ObjectMapDTO<ShopInfoDTO, Object>> buildAreaShopList(OrderRecordRequest request) {
        List<ObjectMapDTO<Integer, Object>> areaShopRank = orderRecordService.findAreaShopRankByType(request);
        if (CollectionUtils.isEmpty(areaShopRank)) {
            return Lists.newArrayList();
        }
        List<Integer> shopIdList = areaShopRank.stream().map(ObjectMapDTO::getDtoObject).collect(Collectors.toList());
        Map<Integer, ShopInfoDTO> shopInfoDTOMap = shopInfoService.batchFindByShopIds(shopIdList);
        List<ObjectMapDTO<ShopInfoDTO, Object>> result = new ArrayList<>();
        areaShopRank.forEach(objectMapDTO -> {
            Integer shopId = objectMapDTO.getDtoObject();
            result.add(new ObjectMapDTO<>(shopInfoDTOMap.get(shopId), objectMapDTO.getNumber()));
        });
        return result;
    }
}
