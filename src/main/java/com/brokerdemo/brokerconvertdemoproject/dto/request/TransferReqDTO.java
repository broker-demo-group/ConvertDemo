package com.brokerdemo.brokerconvertdemoproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferReqDTO {

    private @NotEmpty String ccy;
    private @NotEmpty String amt;
    private @NotEmpty String from;
    private @NotEmpty String to;
    private @NotEmpty String subAcct;
    private String type;
    private String loanTrans;
    private String clientId;

}
