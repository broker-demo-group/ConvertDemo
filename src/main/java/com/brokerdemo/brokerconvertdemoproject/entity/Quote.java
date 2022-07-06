package com.brokerdemo.brokerconvertdemoproject.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okxbrokerdemo.service.entry.ParamMap;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/13  12:00 PM
 **/
@NoArgsConstructor
@Data
public class Quote {
    @SerializedName("baseCcy")
    public String baseCcy;
    @SerializedName("baseSz")
    public String baseSz;
    @SerializedName("clQReqId")
    public String clQReqId;
    @SerializedName("cnvtPx")
    public String cnvtPx;
    @SerializedName("origRfqSz")
    public String origRfqSz;
    @SerializedName("quoteCcy")
    public String quoteCcy;
    @SerializedName("quoteId")
    public String quoteId;
    @SerializedName("quoteSz")
    public String quoteSz;
    @SerializedName("quoteTime")
    public String quoteTime;
    @SerializedName("rfqSz")
    public String rfqSz;
    @SerializedName("rfqSzCcy")
    public String rfqSzCcy;
    @SerializedName("side")
    public String side;
    @SerializedName("ttlMs")
    public String ttlMs;
//    public String


    @Override
    public String toString() {
        ParamMap paramMap = new ParamMap();
        paramMap.add("baseCcy",baseCcy);
        paramMap.add("baseSz",baseSz);
        paramMap.add("clQReqId",clQReqId);
        paramMap.add("cnvtPx",cnvtPx);
        paramMap.add("origRfqSz",origRfqSz);
        paramMap.add("quoteCcy",quoteCcy);
        paramMap.add("quoteId",quoteId);
        paramMap.add("quoteSz",quoteSz);
        paramMap.add("quoteTime",quoteTime);
        paramMap.add("rfqSz",rfqSz);
        paramMap.add("rfqSzCcy",rfqSzCcy);
        paramMap.add("side",side);
        paramMap.add("ttlMs",ttlMs);
        return paramMap.getPayLoadJson();
    }
}
