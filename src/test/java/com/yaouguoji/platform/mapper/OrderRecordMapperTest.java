package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.OrderRecordEntity;
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
public class OrderRecordMapperTest {

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Test
    public void selectAndAddTest() {
        OrderRecordEntity orderRecordEntity = new OrderRecordEntity();
        orderRecordEntity.setUserId(100000000);
        orderRecordEntity.setShopId(100003);
        orderRecordEntity.setOrderChannel("电话下单");
        orderRecordEntity.setOrderStatus("等待配送");
        orderRecordEntity.setProductType("服装");
        orderRecordEntity.setProductList("一个裙子，一双袜子");
        orderRecordEntity.setPayType("现金支付");
        orderRecordEntity.setPrice(new BigDecimal(130));
        orderRecordMapper.addOrderInfo(orderRecordEntity);
        Assert.assertTrue(orderRecordEntity.getOrderId() > 0);
    }

    @Test
    public void testUpdate() {
        OrderRecordEntity orderRecordEntity = orderRecordMapper.selectById(2);
        orderRecordEntity.setPrice(new BigDecimal(50));
        orderRecordEntity.setOrderStatus("等待交易");
        int i = orderRecordMapper.updateOrderInfo(orderRecordEntity);
        Assert.assertTrue(i > 0);
    }

    @Test
    public void testSelect() {
        List<OrderRecordEntity> entityList = orderRecordMapper.selectOrderRecordsByUserId(100000000, new DateTime().minusDays(1).toDate(), new Date());
        List<OrderRecordEntity> entityList2 = orderRecordMapper.selectOrderRecordsByShopId(100003, new DateTime().minusDays(1).toDate(), new Date());
        Assert.assertTrue(entityList.size() == 3 && entityList2.size() == 2);
    }
}
