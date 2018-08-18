package com.n.sell.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("00", "成功"),
    SYS_ERROR("96", "系统故障");

    private String resultCode;

    private String msg;

    ResultCode(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
