package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.ShopInfoDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.OrderRecordService;
import com.yaouguoji.platform.service.ShopInfoService;
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
public class ShopOrderRecordController {

    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private ShopInfoService shopInfoService;

    @GetMapping("/order/shop/{shopId}")
    public CommonResult shopOrder(@PathVariable("shopId") int shopId) {
        if (shopId <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ShopInfoDTO shopInfoDTO = shopInfoService.findShopInfoByShopId(shopId);
        if (shopInfoDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<OrderRecordDTO> ordersByShopId =
                orderRecordService.findOrdersByShopId(shopId, new DateTime().minusMonths(1).toDate(), new Date());
        Map<String, Object> data = new HashMap<>();
        data.put("shopInfo", shopInfoDTO);
        data.put("orderList", ordersByShopId);
        return CommonResult.success(data);
    }
}
