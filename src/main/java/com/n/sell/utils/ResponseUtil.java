package com.n.sell.utils;


import com.n.sell.VO.ResponseVO;
import com.n.sell.enums.ResultEnum;

public class ResponseUtil {

    public static ResponseVO success(Object object){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(ResultEnum.SUCCESS.getCode());
        responseVO.setMsg(ResultEnum.SUCCESS.getMsg());
        responseVO.setData(object);
        return responseVO;
    }
}
