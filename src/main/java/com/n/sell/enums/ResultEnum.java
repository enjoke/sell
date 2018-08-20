package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    product_not_exist(10, "商品不存在")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
