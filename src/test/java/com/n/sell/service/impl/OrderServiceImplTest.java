package com.n.sell.service.impl;

import com.n.sell.dto.OrderDTO;
import com.n.sell.entity.OrderDetail;
import com.n.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "123456";

    private final String ORDERID = "1534770801382528270";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("china");
        orderDTO.setBuyerName("lovely213");
        orderDTO.setBuyerPhone("133333");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("00010001");
        orderDetail.setProductQuantity(1);
        orderDetails.add(orderDetail);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("00010002");
        orderDetail1.setProductQuantity(10);

        orderDetails.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.create(orderDTO);
        log.debug("result {}", result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        log.info("orderDTO {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(1, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        log.info("orderPages {}", orderDTOPage);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        orderService.cancel(orderDTO);

    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        orderService.finish(orderDTO);
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        orderService.paid(orderDTO);
    }

    @Test
    public void findAll(){
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(2, 10));
        Assert.assertTrue("查询订单列表", orderDTOPage.getTotalElements() > 0);
    }
}