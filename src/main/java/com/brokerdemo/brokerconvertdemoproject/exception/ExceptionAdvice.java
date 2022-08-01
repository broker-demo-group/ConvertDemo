package com.brokerdemo.brokerconvertdemoproject.exception;

import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/7  3:02 PM
 **/

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BrokerResponse handleException(Exception e) {
        log.error("Unknown exception", e);
        return BrokerResponse.error(BusinessExceptionEnum.SERVER_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BrokerResponse handleException(BusinessException e) {
        log.error("BusinessException:code:{},msg:{}", e.getErrorCode(),e.getMsg());
        return BrokerResponse.error( e);
    }
}
