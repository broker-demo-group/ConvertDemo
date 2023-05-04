package com.brokerdemo.brokerconvertdemoproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 * @date: 5/2/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealNameKycRequest {

    private String cardNo;
    private String realName;
    private String userName;

}
