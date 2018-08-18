package com.n.sell.service;

import com.n.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderMasterService {

    OrderMaster save(OrderMaster orderMaster);

    Page<OrderMaster>  findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
