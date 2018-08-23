package com.n.sell.controller;


import com.n.sell.VO.ProductInfoVO;
import com.n.sell.VO.ProductVO;
import com.n.sell.VO.ResponseVO;
import com.n.sell.entity.ProductCategory;
import com.n.sell.entity.ProductInfo;
import com.n.sell.service.ProductCategoryService;
import com.n.sell.service.ProductInfoService;
import com.n.sell.utils.ResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
public class ConsumerController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/list")
    public ResponseVO list(){

        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory :productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResponseUtil.success(productVOList);
    }
}
