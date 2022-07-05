package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.entity.ConvertPair;
import com.brokerdemo.brokerconvertdemoproject.entity.Quote;
import com.brokerdemo.brokerconvertdemoproject.entity.QuoteRequest;
import com.google.gson.JsonObject;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.exception.OkxApiException;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  6:46 PM
 **/
@Service
public class ConvertService {

    @Value("${broker.api.isSimulate}")
    boolean isSimulate;

    /**
     * 根据用户的 quoteRequest 进行闪兑交易
     */
    public void convert(QuoteRequest quoteRequest) {
//        quoteRequest -> getQuote -> doConvert
        Client subAccountClient = getSubAccountClint();
        Quote quote = getQuote(subAccountClient, quoteRequest);
        if (!doConvert(subAccountClient, quote)) {
            throw new OkxApiException("convert rejected", 303);
        }
    }

    /**
     * 根据报价 Quote 进行闪对交易
     *
     * @Return boolean 闪兑是否成功，若闪兑全部成功返回ture
     */
    public boolean doConvert(Client subAccountClient, Quote quote) {
        ParamMap param = new ParamMap();
        param.add("quoteId", quote.getQuoteId());
        param.add("baseCcy", quote.getBaseCcy());
        param.add("quoteCcy", quote.getQuoteCcy());
        param.add("side", quote.getSide());
        param.add("sz", quote.getRfqSz());
        param.add("szCcy", quote.getRfqSzCcy());
        JsonObject tradeResult;
        try {
            tradeResult = subAccountClient.getAssetConvert().convertTrade(param, JsonObject.class);
        } catch (OkxApiException e) {
            throw new OkxApiException("doConvert:" + e.getMessage(), 303);
        }
        String state = tradeResult.get("state").getAsString();
        return "fullyFilled".equals(state);

    }

    /**
     * 获取闪兑报价
     */
    public Quote getQuote(Client client, QuoteRequest quoteRequest) {
        ConvertPair convertPair = getConvertPair(client, quoteRequest);

//      根据 convertPair 与quoteRequest 构造查询 Quote 的参数
        String fromCcy = quoteRequest.getFromCcy();
        String side = fromCcy.equals(convertPair.baseCcy) ? "sell" : "buy";
        ParamMap param = new ParamMap();
        param.add("baseCcy", convertPair.getBaseCcy());
        param.add("quoteCcy", convertPair.getQuoteCcy());
        param.add("side", side);
        param.add("rfqSz", quoteRequest.getAmount());
        param.add("rfqSzCcy", quoteRequest.getFromCcy());
        Quote quote;
        try {
            quote = client.getAssetConvert().convertEstimateQuote(param, Quote.class);
        } catch (OkxApiException e) {
            throw new OkxApiException("getConvertQuote:" + e.getMessage(), 302);
        }
        return quote;
    }

    /**
     * 根据 quoteRequest 查询 ConvertPair
     */
    public ConvertPair getConvertPair(Client client, QuoteRequest quoteRequest) {
        ParamMap param = new ParamMap();
        param.add("fromCcy", quoteRequest.getFromCcy());
        param.add("toCcy", quoteRequest.getToCcy());
        ConvertPair convertCurrencyPair;

        try {
            convertCurrencyPair = client.getAssetConvert().getConvertCurrencyPair(param, ConvertPair.class);
        } catch (OkxApiException e) {
            throw new OkxApiException("getConvertPair:" + e.getMessage(), 301);
        }
        return convertCurrencyPair;
    }

    /**临时测试用*/
    @Resource
    Client client;

    public Client getSubAccountClint() {
        // todo 根据当前登录用户对应SubAccount的apikey 和 apiSecret 创建的Client

//        return OkxSDK.getClient("", "", "", isSimulate);
        return client;
    }

}
