package com.n.sell.utils;


import com.n.sell.VO.ResponseVO;
import com.n.sell.enums.ResultCode;

import java.util.List;

public class ResponseUtil {

    public static ResponseVO success(List list){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(ResultCode.SUCCESS.getResultCode());
        responseVO.setMsg(ResultCode.SUCCESS.getMsg());
        responseVO.setData(list);
        return responseVO;
    }
}
