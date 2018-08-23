package com.n.sell.service;

import com.n.sell.dto.OrderDTO;

public interface ConsumerService {
    OrderDTO findOne(String openId, String orderId);

    OrderDTO cancel(String openId, String orderId);
}
