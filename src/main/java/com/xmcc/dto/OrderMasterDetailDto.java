package com.xmcc.dto;

import com.xmcc.commons.OrderEnum;
import com.xmcc.commons.PayEnum;
import com.xmcc.entity.OrderDetail;
import com.xmcc.entity.OrderMaster;
import com.xmcc.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMasterDetailDto extends OrderMaster {

    /** 订单项. */
    List<OrderDetail> details;

    //转换成Dto
    public static OrderMasterDetailDto build(OrderMaster orderMaster) {
        OrderMasterDetailDto orderMasterDetailDto = new OrderMasterDetailDto();
        BeanUtils.copyProperties(orderMaster, orderMasterDetailDto);
        return orderMasterDetailDto;
    }
}
