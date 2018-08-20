package com.n.sell.dto;

import com.n.sell.entity.OrderDetail;
import com.n.sell.enums.OrderStatus;
import com.n.sell.enums.PayStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatus.INIT.getState();

    private Integer payStatus = PayStatus.UNPAID.getState();

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
