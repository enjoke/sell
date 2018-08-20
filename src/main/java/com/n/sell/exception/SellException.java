package com.n.sell.exception;

import com.n.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
