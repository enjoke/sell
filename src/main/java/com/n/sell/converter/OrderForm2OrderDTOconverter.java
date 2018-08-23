package com.n.sell.converter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.n.sell.dto.OrderDTO;
import com.n.sell.entity.OrderDetail;
import com.n.sell.enums.ResultEnum;
import com.n.sell.exception.SellException;
import com.n.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderForm2OrderDTOconverter {
    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try{
            orderDTO.setOrderDetailList(gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}
                    .getType()));
        } catch (Exception e){
            log.error("格式错误{}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }
}
