package com.n.sell.service.impl;

import com.n.sell.entity.OrderDetail;
import com.n.sell.repository.OrderDetailRepository;
import com.n.sell.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
