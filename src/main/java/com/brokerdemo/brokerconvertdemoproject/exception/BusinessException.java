package com.brokerdemo.brokerconvertdemoproject.exception;

import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private Integer errorCode;
    private String msg;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum){
        this.errorCode = businessExceptionEnum.getCode();
        this.msg  = businessExceptionEnum.getMsg();
    }

}
