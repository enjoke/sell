package com.n.sell.controller;

import com.n.sell.dto.OrderDTO;
import com.n.sell.enums.ResultEnum;
import com.n.sell.exception.SellException;
import com.n.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(page - 1, size));
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map){
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e){
            log.error("[取消订单], 取消订单{}时发生异常:{}", orderId, e);
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e){
            log.error("[查询订单详情], 查询订单号{}详情时发生异常{}", orderId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);

        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("[完结订单], 完结订单{}时发生异常{}", orderId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
