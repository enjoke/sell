package com.n.sell.service.impl;


import com.n.sell.entity.ProductInfo;
import com.n.sell.enums.ProductStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() {
        String productId = "123456";
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
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = service.findAll(request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("ssd");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductDescription("sungsumg");
        productInfo.setProductIcon("2333");
        productInfo.setProductStatus(ProductStatus.ON_SALE.getState());
        productInfo.setProductStock(100);
        productInfo.setCategoryType(1);
        ProductInfo result = service.save(productInfo);
        Assert.assertNotNull(result);
    }
}