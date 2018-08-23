package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1, "请求参数有误"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存错误"),
    ORDER_NOT_EXIST(12, "无效订单"),
    ORDER_STATUS_ERROR(13, "订单状态错误"),
    CART_EMPTY(14,"购物车为空"),
    ORDER_OWNER_ERROR(15, "该订单不属于当前用户"),
    PRODUCT_STATUS_ERROR(16, "商品状态错误"),
    LogIN_ERROR(17, "登录失败"),
    SYSTEM_ERROR(96, "系统故障"),
    ORDER_CANCEL_SUCCESS(100, "订单取消成功"),
    ORDER_FINISH_SUCCESS(101, "订单完结成功")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
