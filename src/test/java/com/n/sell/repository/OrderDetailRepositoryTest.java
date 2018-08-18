package com.n.sell.repository;


import com.n.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;


    @Test
    public void saveTest(){
        OrderDetail detail = new OrderDetail();

        detail.setDetailId("123");
        detail.setOrderId("213");
        detail.setProductId("222");
        detail.setProductIcon("x");
        detail.setProductName("吃的");
        detail.setProductPrice(new BigDecimal(2.3));
        detail.setProductQuantity(1);
        Assert.assertNotNull(repository.save(detail));
    }

    @Test
    public void findByOrderId() {
        Assert.assertNotEquals(0, repository.findByOrderId("213").size());
    }
}