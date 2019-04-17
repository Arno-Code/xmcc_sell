package com.xmcc.service;

import com.xmcc.commons.ResultResponse;
import com.xmcc.dto.OrderMasterDto;

public interface OrderMasterService {

    ResultResponse insertOrder(OrderMasterDto orderMasterDto);
}
