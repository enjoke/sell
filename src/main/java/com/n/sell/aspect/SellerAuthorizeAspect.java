package com.n.sell.aspect;

import com.n.sell.constant.CookieConstant;
import com.n.sell.constant.RedisConstant;
import com.n.sell.exception.SellerAuthorizeException;
import com.n.sell.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.n.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.n.sell.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){
            throw new SellerAuthorizeException();
        }

        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_EXPIRE, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            throw new SellerAuthorizeException();
        }
    }
}
