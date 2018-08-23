package com.n.sell.controller;

import com.n.sell.entity.ProductCategory;
import com.n.sell.exception.SellException;
import com.n.sell.form.CategoryForm;
import com.n.sell.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("categoryList", productCategoryList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("category/list");
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map){
        if(categoryId != null){
            ProductCategory productCategory = productCategoryService.findOne(categoryId);
            map.put("category", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = productCategoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            productCategoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "操作成功");
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
