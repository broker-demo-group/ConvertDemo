package com.brokerdemo.brokerconvertdemoproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.okxbrokerdemo.service.APIRequestPayload;
import org.okxbrokerdemo.service.entry.ParamMap;
/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  8:25 AM
 * 询价规则： side 参数是描述 baseCcy 的交易方向；
 *          fromCcy
 *          toCcy
 *          amount
 *
 *          0.1 BTC to ETH
 *         param.add("baseCcy", "ETH");
 *         param.add("quoteCcy", "BTC");
 *         param.add("side", "buy");
 *         param.add("rfqSz", "0.1");
 *         param.add("rfqSzCcy", "BTC");
 *
 *         1.5 ETH to BTC
 *         param.add("baseCcy", "ETH");
 *         param.add("quoteCcy", "BTC");
 *         param.add("side", "sell");
 *         param.add("rfqSz", "1.5");
 *         param.add("rfqSzCcy", "ETH");
 **/
@ApiModel(value = "quote", description = "some description")
public class QuoteRequest  {
    @ApiModelProperty(value = "fromCcy", required = true, example = "BTC")
    private String fromCcy;
    @ApiModelProperty(value = "toCcy", required = true, example = "USDT")
    private String toCcy;
    @ApiModelProperty(value = "amount", required = true, example = "0.1")
    private String amount;

    public String getFromCcy() {
        return fromCcy;
    }

    public void setFromCcy(String fromCcy) {
        this.fromCcy = fromCcy;
    }

    public String getToCcy() {
        return toCcy;
    }

    public void setToCcy(String toCcy) {
        this.toCcy = toCcy;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
