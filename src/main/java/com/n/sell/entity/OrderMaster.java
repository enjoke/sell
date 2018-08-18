package com.n.sell.entity;


import com.n.sell.enums.OrderStatus;
import com.n.sell.enums.PayStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;


    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatus.INIT.getState();

    private Integer payStatus = PayStatus.UNPAID.getState();

    private Date createTime;

    private Date update_time;
}
