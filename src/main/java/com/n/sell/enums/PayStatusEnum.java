package com.n.sell.enums;


import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum{
    UNPAID(0, "未支付"),
    PAYED(1, "已支付"),
    REFUND(2, "已退款")
    ;

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
