package com.yaouguoji.platform.controller;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.*;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.*;
import com.yaouguoji.platform.vo.ObjectMapVO;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camera")
public class AreaController {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private AreaService areaService;
    @Resource
    private CameraService cameraService;
    @Resource
    private CameraRecordService cameraRecordService;
    @Resource
    private ShopInfoService shopInfoService;
    @Resource
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
        Map<Integer, ObjectMapVO<AreaDTO, Integer>> resultMap = Maps.newHashMap();
        cameraRecordDTOS.forEach(cameraRecordDTO -> {
            int areaId = cameraId2AreaIdMap.get(cameraRecordDTO.getCameraId());
            ObjectMapVO<AreaDTO, Integer> objectMapVO = resultMap.get(areaId);
            if (objectMapVO == null) {
                resultMap.put(areaId, new ObjectMapVO<>(areaMap.get(areaId), cameraRecordDTO.getCrNumber()));
            } else {
                objectMapVO.setNumber(objectMapVO.getNumber() + cameraRecordDTO.getCrNumber());
            }
        });
        return CommonResult.success(resultMap.values());
    }

//    /**
//     * 查询一个分区内前几名等商家销售额、成交量的排名
//     *
//     * @param limit
//     * @param start
//     * @param end
//     * @param type
//     * @return
//     */
//    @GetMapping("/areaRank")
//    public CommonResult areaShopRank(int limit, String start, String end, int type){
//        try {
//            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
//            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
//
//            List<AreaDTO> areaDTOS = areaService.selectAll();
//            if (CollectionUtils.isEmpty(areaDTOS)){
//                return CommonResult.fail(HttpStatus.NOT_FOUND);
//            }
//            Map<Integer, AreaDTO> areaMap = areaDTOS.stream().collect(Collectors.toMap(AreaDTO::getAreaId, area -> area));
//            List<ShopInfoDTO> shopInfoDTOS = shopInfoService.findAll();
//            if (CollectionUtils.isEmpty(shopInfoDTOS)){
//                return CommonResult.fail(HttpStatus.NOT_FOUND);
//            }
//            Map<Integer,Integer> shopId2AreaIdMap = shopInfoDTOS
//                    .stream()
//                    .collect(Collectors.toMap(ShopInfoDTO::getShopId,ShopInfoDTO::getRegionId));
//            Map<Integer,ObjectMapVO<AreaDTO,Object>> areaShopMap = Maps.newHashMap();
//            Map<Integer,Object> orderRecords = orderRecordService.findShopIdsRankByOrders(limit,startTime,endTime,type);
//            if (CollectionUtils.isEmpty(orderRecords)){
//                return CommonResult.fail(HttpStatus.NOT_FOUND);
//            }
//            Map<Object,ObjectMapVO<Object,Object>> resultMap = Maps.newHashMap();
//            shopInfoDTOS.forEach(shopInfoDTO -> {
//                Integer areaId = shopId2AreaIdMap.get(shopInfoDTO.getShopId());
//                ObjectMapVO<AreaDTO,Object> objectMapVO = areaShopMap.get(areaId);
//                if (objectMapVO == null){
//                    areaShopMap.put(areaId,new ObjectMapVO<>(areaMap.get(areaId),shopInfoDTO.getShopName()));
//                } else {
//                    objectMapVO.setNumber(objectMapVO.getNumber()+"\n"+shopInfoDTO.getShopName());
//                }
//                resultMap.put(areaId, new ObjectMapVO<>(shopInfoDTO.getShopName(), orderRecords));
//
//            });
//            return CommonResult.success(resultMap);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
//        }
//
//    }


    /**
     * 查询一个分区内前几名等商家销售额、成交量的排名
     *
     * @param limit
     * @param start
     * @param end
     * @param type
     * @return
     */
    @GetMapping("/areaRank")
    public CommonResult areaShopRank(int limit, String start, String end, int type) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            List<AreaDTO> areaDTOS = areaService.selectAll();
            Map<Integer, String> areaMap = areaDTOS.stream().collect(Collectors.toMap(AreaDTO::getAreaId, AreaDTO::getASort));
            if (CollectionUtils.isEmpty(areaDTOS)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ShopInfoDTO> shopInfoDTOS = shopInfoService.findAll();
            Map<Integer, Object> orderRecordsTimesMap = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, 1);
            Map<Integer, Object> orderRecordsPriceMap = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, 2);
            Map<Integer, List<ShopInfoDTO>> areaShopGroupMap = shopInfoDTOS.stream().collect(Collectors.groupingBy(ShopInfoDTO::getRegionId));
            Map<String, Map<String, CountPay>> areaShopMap = Maps.newHashMap();
            for (Map.Entry<Integer, List<ShopInfoDTO>> entry : areaShopGroupMap.entrySet()) {
                Map<String, CountPay> shopMap = Maps.newHashMap();
                List<ShopInfoDTO> shopList = entry.getValue();
                for (ShopInfoDTO shopInfoDTO : shopList) {
                    CountPay countPay = new CountPay();
                    countPay.setPayTimes(orderRecordsTimesMap.getOrDefault(shopInfoDTO.getShopId(), 0));
                    countPay.setPayPrice(orderRecordsPriceMap.getOrDefault(shopInfoDTO.getShopId(), 0));
                    shopMap.put(shopInfoDTO.getShopName(), countPay);
                }
                areaShopMap.put(areaMap.get(entry.getKey()), shopMap);
            }
            return CommonResult.success(areaShopMap);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

    }

    @Data
    private class CountPay {
        private Object payTimes;
        private Object payPrice;

    }
}
