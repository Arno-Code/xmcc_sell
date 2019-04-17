package com.xmcc.service;

import com.xmcc.commons.ResultResponse;
import com.xmcc.entity.ProductInfo;

public interface ProductInfoService {

    ResultResponse queryList();


    ResultResponse<ProductInfo> queryById(String productId);

    //修改商品库存
    void updateProduct(ProductInfo productInfo);
}
