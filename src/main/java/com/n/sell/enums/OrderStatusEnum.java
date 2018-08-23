package com.n.sell.enums;


import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    INIT(0, "新建订单"),
    FINISHED(1, "订单完成"),
    CLOSE(2, "订单关闭"),
    REFUND(3, "已退款")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
