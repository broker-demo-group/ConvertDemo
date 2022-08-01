package com.brokerdemo.brokerconvertdemoproject.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {

    SUCCESS(0,"success"),
    REQUEST_ERROR(400,"param is invalid"),
    SERVER_ERROR(500,"server error"),


    CREATE_COMMON_USER_ERROR(10000,"creat common user error"),
    USER_NOT_EXIST(10001,"user not exist"),
    QUERY_CURRENCY_ERROR(10002,"query currency error"),
    TRANSFER_ERROR(10003,"transfer error"),
    SET_TRANS_OUT_ERROR(10004,"set transfer out error"),
    ;


    private final Integer code;
    private final String msg;

}
