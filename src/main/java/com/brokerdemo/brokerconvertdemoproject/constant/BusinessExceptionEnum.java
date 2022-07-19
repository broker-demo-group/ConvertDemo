package com.brokerdemo.brokerconvertdemoproject.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {

    SUCCESS(0,"success"),
    SERVER_ERROR(500,"server error"),


    CREATE_COMMON_USER_ERROR(10000,"creat common user error"),
    ;
    private final Integer code;
    private final String msg;

}
