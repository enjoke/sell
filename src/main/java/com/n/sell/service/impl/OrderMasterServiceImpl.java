package com.n.sell.service.impl;

import com.n.sell.entity.OrderMaster;
import com.n.sell.repository.OrderMasterRepository;
import com.n.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public OrderMaster save(OrderMaster orderMaster) {
        return orderMasterRepository.save(orderMaster);
    }

    @Override
    public Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable) {
        return orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
    }
}
