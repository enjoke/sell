package com.n.sell.service;

import com.n.sell.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetail save(OrderDetail orderDetail);

    List<OrderDetail> findByOrderId(String orderId);
}
