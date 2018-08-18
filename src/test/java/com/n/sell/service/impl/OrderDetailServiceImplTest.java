package com.n.sell.service.impl;

import com.n.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceImplTest {

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @Test
    public void save() {
        OrderDetail detail = new OrderDetail();

        detail.setDetailId("13");
        detail.setOrderId("213");
        detail.setProductId("222");
        detail.setProductIcon("x");
        detail.setProductName("吃的");
        detail.setProductPrice(new BigDecimal(2.3));
        detail.setProductQuantity(1);
        Assert.assertNotNull(orderDetailService.save(detail));
    }

    @Test
    public void findByOrderId() {
        Assert.assertNotEquals(0, orderDetailService.findByOrderId("12"));
    }
}