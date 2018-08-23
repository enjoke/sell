package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum{
    DOWN(0, "下架"),
    UP(1, "上架")
    ;
    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
