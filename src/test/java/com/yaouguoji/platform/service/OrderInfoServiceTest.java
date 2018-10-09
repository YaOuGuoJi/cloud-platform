package com.yaouguoji.platform.service;

import com.github.pagehelper.PageInfo;
import com.yaouguoji.platform.dto.ObjectMapDTO;
import com.yaouguoji.platform.dto.OrderRecordDTO;
import com.yaouguoji.platform.dto.OrderRecordRequest;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

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
    public void testAreaOrderRank() {
        OrderRecordRequest orderRecordRequest = new OrderRecordRequest();
        orderRecordRequest.setId(1);
        orderRecordRequest.setLimit(10);
        orderRecordRequest.setStartTime(new DateTime().minusDays(10).toDate());
        orderRecordRequest.setEndTime(new Date());
        orderRecordRequest.setType(1);
        List<ObjectMapDTO<Integer, Object>> areaShopRankByType = orderRecordService.findAreaShopRankByType(orderRecordRequest);
        Assert.assertFalse(CollectionUtils.isEmpty(areaShopRankByType));
    }

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

    @Test
    public void testPageFind() {
        PageInfo<OrderRecordDTO> pageInfo =
                orderRecordService.pageFindOrderRecordByUserId(100000000, 2, 1,
                        new DateTime(2000, 1, 1, 0, 0, 0).toDate(), new Date());
        Assert.assertNotNull(pageInfo);
    }
}
