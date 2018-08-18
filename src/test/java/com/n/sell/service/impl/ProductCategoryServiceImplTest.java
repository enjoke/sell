package com.n.sell.service.impl;

import com.n.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl service;

    @Test
    public void findOne() {
        ProductCategory productCategory = service.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = service.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());

    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = service.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("emmm", 8);
        ProductCategory result = service.save(productCategory);
        Assert.assertNotNull(result);
    }
}