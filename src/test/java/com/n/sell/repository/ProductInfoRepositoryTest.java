package com.n.sell.repository;

import com.n.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {


    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("ssd");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductDescription("sungsumg");
        productInfo.setProductIcon("2333");
        productInfo.setProductStatus(1);
        productInfo.setProductStock(100);
        productInfo.setCategoryType(1);
        repository.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = repository.findByProductStatus(1);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}