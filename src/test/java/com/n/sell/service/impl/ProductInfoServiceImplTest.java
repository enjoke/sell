package com.n.sell.service.impl;


import com.n.sell.entity.ProductInfo;
import com.n.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    private final String productId = "123456";

    @Test
    public void findOne() {
        ProductInfo productInfo = service.findOne(productId);
        Assert.assertEquals(productId, productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = service.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0, 10);
        Page<ProductInfo> productInfoPage = service.findAll(request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Before
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        productInfo.setProductName("ssd");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductDescription("sungsumg");
        productInfo.setProductIcon("2333");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductStock(100);
        productInfo.setCategoryType(1);
        ProductInfo result = service.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo productInfo = service.findOne("00010001");
        Assert.assertTrue("商品上架",
                service.onSale(productInfo.getProductId()).getProductStatus().equals(ProductStatusEnum.UP.getCode()));
    }

    @Test
    public void offSale(){
        ProductInfo productInfo = service.findOne("00010001");
        Assert.assertTrue("商品下架",
                service.offSale(productInfo.getProductId()).getProductStatus().equals(ProductStatusEnum.DOWN.getCode()));
    }
}