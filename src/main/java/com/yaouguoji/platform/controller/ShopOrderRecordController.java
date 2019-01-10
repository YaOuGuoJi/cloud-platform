package com.yaouguoji.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xianbester.api.dto.AreaDTO;
import com.xianbester.api.dto.ObjectMapDTO;
import com.xianbester.api.dto.OrderRecordDTO;
import com.xianbester.api.dto.ShopInfoDTO;
import com.xianbester.api.service.AreaService;
import com.xianbester.api.service.OrderRecordService;
import com.xianbester.api.service.ShopInfoService;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.util.ShopInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/9/4
 */
@RestController
public class ShopOrderRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderRecordController.class);

    @Reference
    private OrderRecordService orderRecordService;
    @Reference
    private ShopInfoService shopInfoService;
    @Reference
    private AreaService areaService;

    @GetMapping("/shop/order/page")
    public CommonResult shopOrder(int pageNum, int pageSize, String start, String end) {
        int shopId = ShopInfoUtil.getShopId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByShopId(shopId);
            if (shopInfoDTO == null) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            PageInfo<OrderRecordDTO> pageInfo =
                    orderRecordService.pageFindOrderRecordByShopId(shopId, pageNum, pageSize, startTime, endTime);
            return new CommonResultBuilder()
                    .code(200)
                    .message("查询成功！")
                    .data("shopInfo", shopInfoDTO)
                    .data("pageInfo", pageInfo)
                    .build();
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/shop/order/rank")
    public CommonResult shopOrderRankTop(int limit, String start, String end, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (limit <= 0 || startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            Map<Integer, Object> shopIds2ResultMap
                    = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, type);
            if (CollectionUtils.isEmpty(shopIds2ResultMap)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            Map<Integer, ShopInfoDTO> shopInfoDTOMap = shopInfoService.batchFindByShopIds(shopIds2ResultMap.keySet());
            List<ObjectMapDTO> resultList = Lists.newArrayList();
            for (Map.Entry<Integer, ShopInfoDTO> entry : shopInfoDTOMap.entrySet()) {
                resultList.add(new ObjectMapDTO<>(entry.getValue(), shopIds2ResultMap.get(entry.getKey())));
            }
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/shop/order/area")
    public CommonResult areaShopOrder(String start, String end, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            List<AreaDTO> areaDTOS = areaService.selectAll();
            Map<Integer, Object> areaId2NumberMap = orderRecordService.findAreaOrderNumber(startTime, endTime, type);
            List<ObjectMapDTO> resultList = Lists.newArrayList();
            areaDTOS.forEach(areaDTO -> {
                ObjectMapDTO objectMapDTO =
                        new ObjectMapDTO<>(areaDTO, areaId2NumberMap.getOrDefault(areaDTO.getAreaId(), 0));
                resultList.add(objectMapDTO);
            });
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }
}
