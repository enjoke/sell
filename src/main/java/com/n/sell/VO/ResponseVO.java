package com.n.sell.VO;

import lombok.Data;

@Data
public class ResponseVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
