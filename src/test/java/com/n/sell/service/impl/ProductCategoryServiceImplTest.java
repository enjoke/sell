package com.n.sell.service.impl;

import com.n.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl service;
    private final Integer categoryType = 1;
    private final String categoryName = "电脑配件";
    private Integer categoryId;

    @Before
    public void save() {
        ProductCategory productCategory = new ProductCategory(categoryName, categoryType);
        ProductCategory result = service.save(productCategory);
        categoryId = result.getCategoryId();
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        ProductCategory productCategory = service.findOne(categoryId);
        Assert.assertEquals(new Integer(categoryId), productCategory.getCategoryId());
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
}