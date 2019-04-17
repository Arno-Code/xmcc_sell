package com.xmcc.service.impl;

import com.xmcc.commons.*;
import com.xmcc.dto.OrderDetailDto;
import com.xmcc.dto.OrderMasterDto;
import com.xmcc.entity.OrderDetail;
import com.xmcc.entity.OrderMaster;
import com.xmcc.entity.ProductInfo;
import com.xmcc.exception.CustomException;
import com.xmcc.repository.OrderDetailRepository;
import com.xmcc.repository.OrderMasterRepository;
import com.xmcc.service.OrderDetailService;
import com.xmcc.service.OrderMasterService;
import com.xmcc.service.ProductInfoService;
import com.xmcc.utils.BigDecimalUtil;
import com.xmcc.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private ProductInfoService infoService;

    @Autowired
    private OrderMasterRepository masterRepository;

    /**
     * 生成订单
     * @param orderMasterDto
     * @return
     */
    @Override
    @Transactional
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //controller做参数验证，这里直接取出OrderDetailDto
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建订单项集合
        ArrayList<OrderDetail> detailList = new ArrayList<>();
        //创建订单总金额为0  涉及到钱的都用 高精度计算
        BigDecimal totalPrice = new BigDecimal("0");
        //生成订单id
        String orderId = IDUtils.createIdbyUUID();
        //遍历items 生成订单项集合
        for (OrderDetailDto item : items
             ) {
            ResultResponse<ProductInfo> resultResponse = infoService.queryById(item.getProductId());
            //说明该商品未查询到 生成订单失败，因为这儿涉及到事务 需要抛出异常 事务机制是遇到异常才会回滚
            if (resultResponse.getCode() == ResultEnums.FAIL.getCode()){
                throw new CustomException(resultResponse.getMsg());
            }
            ProductInfo productInfo = resultResponse.getData();
            //判断库存
            if (item.getProductQuantity() > productInfo.getProductStock()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //将前台传入的订单项DTO与数据库查询到的 商品数据组装成OrderDetail 放入集合中  @builder
            OrderDetail detail = OrderDetail.builder()
                    .detailId(IDUtils.createIdbyUUID()).productIcon(productInfo.getProductIcon())
                    .productId(item.getProductId()).productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice()).productQuantity(item.getProductQuantity())
                    .orderId(orderId).createTime(new Date()).updateTime(new Date())
                    .build();
            detailList.add(detail);
            //减少商品库存
            productInfo.setProductStock(productInfo.getProductStock() - item.getProductQuantity());
            infoService.updateProduct(productInfo);
            //计算订单价格
            totalPrice = BigDecimalUtil
                    .add(totalPrice, BigDecimalUtil
                            .multi(productInfo.getProductPrice(), item.getProductQuantity()));
        }
        //生成订单 构建订单信息  日期等都用默认的即可
        OrderMaster orderMaster = OrderMaster.builder().buyerAddress(orderMasterDto.getAddress()).buyerName(orderMasterDto.getName())
                .buyerOpenid(orderMasterDto.getOpenid()).orderStatus(OrderEnum.NEW.getCode())
                .payStatus(PayEnum.WAIT.getCode()).buyerPhone(orderMasterDto.getPhone()).createTime(new Date()).updateTime(new Date())
                .orderId(orderId).orderAmount(totalPrice).build();
        //批量插入订单项
        detailService.batchInsert(detailList);
        //插入订单
        masterRepository.save(orderMaster);
        //按照前台要求的数据结构传入
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }
}
