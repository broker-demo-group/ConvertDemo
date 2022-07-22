package com.brokerdemo.brokerconvertdemoproject.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithDrawalRequest {

    private String ccy;
    private String amount;
    private String dest;
    private String toAddr;
}
