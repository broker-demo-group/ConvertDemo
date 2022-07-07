package com.brokerdemo.brokerconvertdemoproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  8:25 AM
 * 询价规则： side 参数是描述 baseCcy 的交易方向；
 * fromCcy
 * toCcy
 * amount
 * <p>
 * 0.1 BTC to ETH
 * param.add("baseCcy", "ETH");
 * param.add("quoteCcy", "BTC");
 * param.add("side", "buy");
 * param.add("rfqSz", "0.1");
 * param.add("rfqSzCcy", "BTC");
 * <p>
 * 1.5 ETH to BTC
 * param.add("baseCcy", "ETH");
 * param.add("quoteCcy", "BTC");
 * param.add("side", "sell");
 * param.add("rfqSz", "1.5");
 * param.add("rfqSzCcy", "ETH");
 **/
@ApiModel(value = "ConvertRequest", description = "some description")
public class ConvertRequest extends QuoteRequest{
    @ApiModelProperty(value = "fromCcy", required = true, example = "USDT")
    private String fromCcy;
    @ApiModelProperty(value = "toCcy", required = true, example = "BTC")
    private String toCcy;
    @ApiModelProperty(value = "amount", required = true, example = "10")
    private String amount;

    // funding\trading\both
    @ApiModelProperty(value = "mode", required = true, example = "both")
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

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
