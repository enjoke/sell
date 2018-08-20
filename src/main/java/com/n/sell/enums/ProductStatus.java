package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ON_SALE(1, "上架"),
    SALE_OUT(0, "下架")
    ;
    private Integer state;

    private String msg;

    ProductStatus(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
