package com.xmcc.service;

import com.xmcc.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    //批量插入OrderDetail
    void batchInsert(List<OrderDetail> orderDetailList);

    List<OrderDetail> findDetailByOrderId (String orderId);
}
