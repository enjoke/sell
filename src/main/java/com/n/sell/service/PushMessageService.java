package com.n.sell.service;

import com.n.sell.dto.OrderDTO;

public interface PushMessageService {
    void orderStatus(OrderDTO orderDTO);
}
