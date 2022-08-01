package com.brokerdemo.brokerconvertdemoproject.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawlReqDTO {

    @SerializedName("ccy")
    private String ccy;
    @SerializedName("amt")
    private String amt;
    @SerializedName("dest")
    private String dest;
    @SerializedName("toAddr")
    private String toAddr;
    @SerializedName("chain")
    private String chain;
    @SerializedName("clientId")
    private String clientId;

}
