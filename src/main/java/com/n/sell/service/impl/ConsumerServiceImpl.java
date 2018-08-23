package com.n.sell.service.impl;

import com.n.sell.dto.OrderDTO;
import com.n.sell.enums.ResultEnum;
import com.n.sell.exception.SellException;
import com.n.sell.service.ConsumerService;
import com.n.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOne(String openId, String orderId) {
        return checkOrderOwner(openId, orderId);
    }

    @Override
    public OrderDTO cancel(String openId, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openId, orderId);
        if(orderDTO == null){
            log.error("[取消订单], 订单{}不存在", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openId, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }

        if(!orderDTO.getBuyerOpenid().equals(openId)){
            log.error("[查询订单]：openid不一致");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }
}
