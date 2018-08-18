package com.n.sell.repository;

import com.n.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("hf");
        orderMaster.setBuyerName("213");
        orderMaster.setBuyerOpenid("233");
        orderMaster.setBuyerPhone("111");
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        orderMaster.setOrderId("123456");
        Assert.assertNotNull(repository.save(orderMaster));
    }

    @Test
    public void findByBuyerOpenid() {

        PageRequest request = new PageRequest(0, 1);
        Assert.assertNotEquals(0, repository.findByBuyerOpenid("233", request).getTotalElements());
    }


}