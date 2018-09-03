package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.OrderRecordDTO;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderInfoServiceTest {

    @Resource
    private OrderRecordService orderRecordService;

    @Test
    public void testAdd() {
        OrderRecordDTO orderRecordDTO = new OrderRecordDTO();
        orderRecordDTO.setUserId(100000000);
        orderRecordDTO.setShopId(100001);
        orderRecordDTO.setOrderChannel("网络下单");
        orderRecordDTO.setOrderStatus("正在出库");
        orderRecordDTO.setProductType("衣服");
        orderRecordDTO.setProductList("鞋一双");
        orderRecordDTO.setPayType("微信支付");
        orderRecordDTO.setPrice(new BigDecimal(700));
        int result = orderRecordService.addOrderInfo(orderRecordDTO);
        Assert.assertTrue(result > 1);
    }

    @Test
    public void testSelect() {
        OrderRecordDTO orderRecordDTO = orderRecordService.findOrderDetailsByOrderId(4);
        Assert.assertTrue(orderRecordDTO != null);
        List<OrderRecordDTO> byUser = orderRecordService.findOrdersByUserId(100000000, new DateTime().minusDays(1).toDate(), new Date());
        Assert.assertTrue(byUser.size() == 1);
        List<OrderRecordDTO> byShop = orderRecordService.findOrdersByShopId(100003, new DateTime().minusDays(3).toDate(), new Date());
        Assert.assertTrue(byShop.size() == 2);
    }
}
