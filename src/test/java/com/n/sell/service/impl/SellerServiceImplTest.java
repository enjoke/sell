package com.n.sell.service.impl;

import com.n.sell.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;
    private final String OPENID = "abc123";

    @Test
    public void findSellerInfoByOpenid() {
        Assert.assertTrue("根据openid查找卖家信息", sellerService.findSellerInfoByOpenid(OPENID) != null);
    }
}