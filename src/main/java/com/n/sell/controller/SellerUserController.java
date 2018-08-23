package com.n.sell.controller;

import com.n.sell.config.ProjectUrlConfig;
import com.n.sell.constant.CookieConstant;
import com.n.sell.constant.RedisConstant;
import com.n.sell.entity.SellerInfo;
import com.n.sell.enums.ResultEnum;
import com.n.sell.service.SellerService;
import com.n.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LogIN_ERROR);
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_EXPIRE, token), openid, expire, TimeUnit.SECONDS);

        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + projectUrlConfig.getProjectUrl() +"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map){
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_EXPIRE, cookie.getValue()));
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        return new ModelAndView("redirect:" + projectUrlConfig.getProjectUrl() +"/sell/seller/order/list");
    }
}
