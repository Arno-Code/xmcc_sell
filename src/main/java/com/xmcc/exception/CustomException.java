package com.xmcc.exception;

import com.xmcc.commons.ResultEnums;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private int code;

    public CustomException() {
        this.code = ResultEnums.FAIL.getCode();
    }

    public CustomException(String message) {
        this(ResultEnums.FAIL.getCode(),message);
    }

    public CustomException(int code,String message) {
        super(message);
        this.code = code;
    }
}
