package com.brokerdemo.brokerconvertdemoproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.okxbrokerdemo.service.APIRequestPayload;
import org.okxbrokerdemo.service.entry.ParamMap;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  8:25 AM
 **/
@ApiModel(value = "quote", description = "some description")
public class Quote implements APIRequestPayload {
    @ApiModelProperty(value = "baseCcy", required = true, example = "BTC")
    private String baseCcy;
    @ApiModelProperty(value = "quoteCcy", required = true, example = "USDT")
    private String quoteCcy;
    @ApiModelProperty(value = "side", required = true, allowableValues = "sell,buy", example = "sell")
    private String side;

    @ApiModelProperty(value = "rfqSz", required = true, example = "0.1")
    private String rfqSz;


    @ApiModelProperty(value = "rfqSzCcy", required = true,  example = "BTC")
    private String rfqSzCcy;

    public String getBaseCcy() {
        return baseCcy;
    }

    public void setBaseCcy(String baseCcy) {
        this.baseCcy = baseCcy;
    }

    public String getQuoteCcy() {
        return quoteCcy;
    }

    public void setQuoteCcy(String quoteCcy) {
        this.quoteCcy = quoteCcy;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getRfqSz() {
        return rfqSz;
    }

    public void setRfqSz(String rfqSz) {
        this.rfqSz = rfqSz;
    }

    public String getRfqSzCcy() {
        return rfqSzCcy;
    }

    public void setRfqSzCcy(String rfqSzCcy) {
        this.rfqSzCcy = rfqSzCcy;
    }

    @Override
    public String getPayLoadJson() {
        ParamMap param = new ParamMap();
        param.add("baseCcy", baseCcy);
        param.add("quoteCcy",quoteCcy);
        param.add("side", side);
        param.add("rfqSz", rfqSz);
        param.add("rfqSzCcy", rfqSzCcy);
        return param.getPayLoadJson();
    }
}
