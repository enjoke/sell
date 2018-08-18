package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ON_SALE(0, "上架"),
    OUT_SALE(1, "下架")
    ;
    private Integer state;

    private String msg;

    ProductStatus(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
