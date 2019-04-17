package com.xmcc.service.impl;

import com.xmcc.commons.ProductEnums;
import com.xmcc.commons.ResultEnums;
import com.xmcc.commons.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.entity.ProductInfo;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.ProductCategoryService;
import com.xmcc.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductInfoRepository infoRepository;

    /**
     * 商品列表：分类显示商品信息
     * @return
     */
    @Override
    public ResultResponse queryList() {
        //获取分类
        ResultResponse<List<ProductCategoryDto>> resultResponse = categoryService.findAll();
        List<ProductCategoryDto> categoryDtoList = resultResponse.getData();
        //如果分类为空直接返回
        if (CollectionUtils.isEmpty(categoryDtoList)){
            return resultResponse;
        }
        //获取分类的type集合
        List<Integer> typeList = categoryDtoList.stream().map(ProductCategoryDto::getCategoryType).collect(Collectors.toList());
        //通过分类查询到的上架的菜品
        List<ProductInfo> infoList = infoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);
        //将菜品信息封装进分类
        List<ProductCategoryDto> categoryDtos = categoryDtoList.parallelStream()
                .map(categoryDto -> {
                    categoryDto.setProductInfoDtoList(
                            infoList.stream()
                                    .filter(info -> info.getCategoryType() == categoryDto.getCategoryType())
                                    .map(ProductInfoDto::build).collect(Collectors.toList()));
                    return categoryDto;
                }).collect(Collectors.toList());
        return ResultResponse.success(categoryDtos);
    }

    /**
     * 通过id查询商品信息
     * @return ResultResponse<ProductInfo>
     */
    @Override
    public ResultResponse<ProductInfo> queryById(String productId) {
        //使用common-lang3 jar的类判断参数
        if (StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg()+"_"+productId);
        }
        //通过id查询
        Optional<ProductInfo> infoOptional = infoRepository.findById(productId);
        //判断商品是否存在
        if (!infoOptional.isPresent()){//isPresent():如果值存在返回true，否则返回false。
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getMsg()+"_"+productId);
        }
        //判断商品是否下架
        ProductInfo productInfo = infoOptional.get();
        if (productInfo.getProductStatus() == ResultEnums.PRODUCT_DOWN.getCode()){
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg()+"_"+productId);
        }

        return ResultResponse.success(productInfo);
    }

    /**
     * 修改商品信息
     */
    @Override
    @Transactional
    public void updateProduct(ProductInfo productInfo) {
        infoRepository.save(productInfo);
    }
}
