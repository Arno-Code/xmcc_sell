package com.xmcc.controller;


import com.xmcc.commons.ResultResponse;
import com.xmcc.service.ProductCategoryService;
import com.xmcc.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("buyer/product")
@Api(description = "商品信息接口")//使用swagger2的注解对类描述
public class ProductInfoController {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductInfoService infoService;


    /**
     * 商品列表
     * @return
     */
    @RequestMapping("/list")
    @ApiOperation("商品列表")
    public ResultResponse list(){
        ResultResponse resultResponse = infoService.queryList();
        return resultResponse;
    }



}
