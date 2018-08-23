package com.n.sell.controller;

import com.n.sell.VO.ResponseVO;
import com.n.sell.converter.OrderForm2OrderDTOconverter;
import com.n.sell.dto.OrderDTO;
import com.n.sell.enums.ResultEnum;
import com.n.sell.exception.SellException;
import com.n.sell.form.OrderForm;
import com.n.sell.service.ConsumerService;
import com.n.sell.service.OrderService;
import com.n.sell.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class ConsumerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ConsumerService consumerService;

    @PostMapping(value = "/create")
    public ResponseVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("参数错误");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOconverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("购物车为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResponseUtil.success(map);
    }


    @GetMapping(value = "/list")
    public ResponseVO<List<OrderDTO>> list(@RequestParam("openid") String openId,
                                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openId)){
            log.error("[订单查询],缺少openid");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), "缺少openid");
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openId, pageRequest);
        return ResponseUtil.success(orderDTOPage.getContent());
    }

    @GetMapping(value = "/detail")
    public ResponseVO<OrderDTO> detail(@RequestParam("openid") String openId,
                                       @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = consumerService.findOne(openId, orderId);
        return ResponseUtil.success(orderDTO);
    }

    @PostMapping(value = "/cancel")
    public ResponseVO cancel(@RequestParam("openid") String openId,
                             @RequestParam("orderId") String orderId){
        consumerService.cancel(openId, orderId);
        return ResponseUtil.success(null);
    }

 }
