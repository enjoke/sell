package com.n.sell.enums;


import lombok.Getter;

@Getter
public enum PayStatus {
    UNPAID(0, "未支付"),
    PAYED(1, "已支付"),
    REFUND(2, "已退款")
    ;

    private Integer state;

    private String msg;

    PayStatus(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
