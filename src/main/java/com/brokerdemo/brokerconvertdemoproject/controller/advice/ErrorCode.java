package com.brokerdemo.brokerconvertdemoproject.controller.advice;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/18  3:06 PM
 **/
public interface ErrorCode {

    // 通用异常
    int UNDEFINED_ERROR = 1;
    int DB_ERROR = 2;

    // 用户异常
    int USER_NOT_LOGIN = 101;
    int USER_LOGIN_FAILURE = 102;

    // 业务异常
    int CONVERT_ERROR = 201;
    int REQUIRE_CONVERTPAIR_ERROR = 202;
    int REQUIRE_QUOTE_ERROR = 203;
    int BALANCE_INSUFFICIENT = 204;


}
