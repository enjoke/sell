package com.n.sell.repository;

import com.n.sell.entity.SellerInfo;
import com.n.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    private final String OPENID = "abc123";

    private final String NAME = "233";
    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid(OPENID);
        sellerInfo.setPassword("123456");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername(NAME);
        Assert.assertTrue("卖家信息保存", sellerInfoRepository.save(sellerInfo) != null );
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(OPENID);
        Assert.assertTrue("findByOpenid", sellerInfoRepository.save(sellerInfo) != null );
    }
}