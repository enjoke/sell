package com.n.sell.service;

import com.n.sell.entity.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
