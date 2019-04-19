package com.xmcc.commons;

import lombok.Getter;
import sun.print.ServiceDialog;

@Getter
public enum ResultEnums {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    PRODUCT_UP(0,"正常"),
    PRODUCT_DOWN(1,"商品下架"),
    NOT_EXITS(1,"商品不存在"),
    NOT_ORDER_EXITS(1,"订单不存在"),
    PARAM_ERROR(1,"请求参数异常");

    private int code;
    private String msg;

    ResultEnums(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
