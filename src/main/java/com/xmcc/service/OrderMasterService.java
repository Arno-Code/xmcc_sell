package com.xmcc.service;

import com.xmcc.commons.ResultResponse;
import com.xmcc.dto.OrderMasterDto;
import com.xmcc.dto.OrderMasterPageBean;

public interface OrderMasterService {

    ResultResponse insertOrder(OrderMasterDto orderMasterDto);

    ResultResponse findByOrderMasterPageBean(OrderMasterPageBean orderMasterPageBean);

    ResultResponse findOrderMasterDetail(String openId,String orderId);

    ResultResponse cancelOrderMaster(String openId, String orderId);
}
