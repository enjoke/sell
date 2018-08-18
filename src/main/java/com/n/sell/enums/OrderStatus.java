package com.n.sell.enums;


import lombok.Getter;

@Getter
public enum OrderStatus {
    INIT(0, "新建订单"),
    FINISHED(1, "订单完成"),
    CLOSE(2, "订单关闭"),
    REFUND(3, "已退款")
    ;

    private Integer state;

    private String msg;

    OrderStatus(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
