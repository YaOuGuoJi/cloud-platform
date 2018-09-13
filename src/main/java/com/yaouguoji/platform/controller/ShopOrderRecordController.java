package com.yaouguoji.platform.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.common.CommonResultBuilder;
import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.AreaService;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.ShopInfoService;
import com.yaouguoji.platform.vo.ObjectMapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private ShopInfoService shopInfoService;
    @Resource
    private AreaService areaService;

    @GetMapping("/order/shop/page")
    public CommonResult shopOrder(int shopId, int pageNum, int pageSize, String start, String end) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            if (shopId <= 0 || startTime.after(endTime)) {
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

    @GetMapping("/order/shop/rank")
    public CommonResult shopOrderRankTop(int limit, String start, String end, int type) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            if (limit <= 0 || startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            Map<Integer, Object> shopIds2ResultMap
                    = orderRecordService.findShopIdsRankByOrders(limit, startTime, endTime, type);
            if (CollectionUtils.isEmpty(shopIds2ResultMap)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ShopInfoDTO> shopInfoDTOs
                    = shopInfoService.batchFindByShopIdList(new ArrayList<>(shopIds2ResultMap.keySet()));
            List<ObjectMapVO> resultList = Lists.newArrayList();
            shopInfoDTOs.forEach(shopInfoDTO ->
                resultList.add(new ObjectMapVO(shopInfoDTO, shopIds2ResultMap.get(shopInfoDTO.getShopId())))
            );
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/order/shop/area")
    public CommonResult areaShopOrder(String start, String end, int type) {
        try {
            Date startTime = SIMPLE_DATE_FORMAT.parse(start);
            Date endTime = SIMPLE_DATE_FORMAT.parse(end);
            if (startTime.after(endTime)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
            List<AreaDTO> areaDTOS = areaService.selectAll();
            Map<Integer, Object> areaId2NumberMap = orderRecordService.findAreaOrderNumber(startTime, endTime, type);
            List<ObjectMapVO> resultList = Lists.newArrayList();
            areaDTOS.forEach(areaDTO -> {
                ObjectMapVO objectMapVO =
                        new ObjectMapVO(areaDTO, areaId2NumberMap.getOrDefault(areaDTO.getAreaId(), 0));
                resultList.add(objectMapVO);
            });
            return CommonResult.success(resultList);
        } catch (ParseException e) {
            LOGGER.error("解析时间异常!", e);
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }
}
