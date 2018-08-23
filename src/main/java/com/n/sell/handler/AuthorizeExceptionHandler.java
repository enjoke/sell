package com.n.sell.handler;

import com.n.sell.config.ProjectUrlConfig;
import com.n.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AuthorizeExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handleAuthorizeException(){
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWxOpenAuthorizeUrl())
        .concat("/sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlConfig.getProjectUrl())
        .concat("/sell/seller/login"));
    }
}
