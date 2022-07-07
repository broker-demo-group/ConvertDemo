package com.brokerdemo.brokerconvertdemoproject.controller.advice;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.mongodb.MongoException;
import org.okxbrokerdemo.exception.OkxApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;
/**
 * @author: bowen
 * @description: 
 * @date: 2022/7/7  3:02 PM
 **/
//@ControllerAdvice
public class ExceptionAdvice implements ErrorCode {

    private Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({OkxApiException.class, MongoException.class})
    @ResponseBody
    public BrokerResponse handleException(Exception e) {
        int code;
        String msg;

        if (e instanceof OkxApiException) {
            code = ((OkxApiException) e).getCode();
            msg = e.getMessage();
        } else if (e instanceof MongoException) {
            code = DB_ERROR;
            msg= "数据库发生未知错误，该资源不存在！";
        } else {
            code = UNDEFINED_ERROR;
            msg = "发生未知错误！";
            // 记录日志

        }
        logger.error(msg, e);
        System.out.println(e.getClass());
        return new BrokerResponse(code,"",msg);
    }

}
