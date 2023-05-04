package com.brokerdemo.brokerconvertdemoproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 * @date: 4/4/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliKycResult<T> {

    private Integer error_code;
    private String reason;
    private T result;
}
