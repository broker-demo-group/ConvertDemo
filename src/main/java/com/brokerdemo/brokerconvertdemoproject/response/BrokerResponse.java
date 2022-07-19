package com.brokerdemo.brokerconvertdemoproject.response;

import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  10:20 AM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrokerResponse<T> {

    private Integer code;
    private T data;
    private String message;


    public static BrokerResponse error(BusinessExceptionEnum businessExceptionEnum) {
        return BrokerResponse.builder()
                .code(businessExceptionEnum.getCode())
                .message(businessExceptionEnum.getMsg())
                .build();
    }

    public static BrokerResponse success() {
        return BrokerResponse.builder()
                .code(BusinessExceptionEnum.SUCCESS.getCode())
                .message(BusinessExceptionEnum.SUCCESS.getMsg())
                .build();

    }

    public static <T> BrokerResponse success(T data) {
        return BrokerResponse.builder()
                .code(BusinessExceptionEnum.SUCCESS.getCode())
                .data(data)
                .message(BusinessExceptionEnum.SUCCESS.getMsg())
                .build();

    }


}
