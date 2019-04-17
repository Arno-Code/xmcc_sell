package com.xmcc.service.impl;

import com.xmcc.commons.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.entity.ProductCategory;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository category;

    @Override
    public ResultResponse<List<ProductCategoryDto>> findAll() {
        //查询菜品分类并封装dto
        List<ProductCategory> categoryList = category.findAll();
        List<ProductCategoryDto> categoryDtoList = categoryList.stream().map(category -> ProductCategoryDto.build(category)).
                collect(Collectors.toList());
        return ResultResponse.success(categoryDtoList);
    }
}
