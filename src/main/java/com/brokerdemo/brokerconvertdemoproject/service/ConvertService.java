package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.ApiParam;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.BalanceEntity;
import com.brokerdemo.brokerconvertdemoproject.entity.ConvertPair;
import com.brokerdemo.brokerconvertdemoproject.entity.ConvertRequest;
import com.brokerdemo.brokerconvertdemoproject.entity.Quote;
import com.brokerdemo.brokerconvertdemoproject.entity.QuoteRequest;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.google.gson.JsonObject;
import com.mongodb.MongoException;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.exception.OkxApiException;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.brokerdemo.brokerconvertdemoproject.controller.advice.ErrorCode.CONVERT_ERROR;
import static com.brokerdemo.brokerconvertdemoproject.controller.advice.ErrorCode.REQUIRE_CONVERTPAIR_ERROR;
import static com.brokerdemo.brokerconvertdemoproject.controller.advice.ErrorCode.REQUIRE_QUOTE_ERROR;


/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  6:46 PM
 **/
@Service
public class ConvertService {


    @Resource
    AccountService accountService;

    /**
     * 根据用户的 quoteRequest 进行闪兑交易
     */
    public void convert(ConvertRequest convertRequest, String username) {
//        ConvertRequest -> getQuote -> doConvert

        Client subAccountClient = accountService.getSubAccountClint(username);
        if(!checkQuote(subAccountClient,convertRequest)){
            throw new OkxApiException("balance insufficient", CONVERT_ERROR);
        }
        Quote quote = getQuote(subAccountClient, convertRequest);
        if (!doConvert(subAccountClient, quote)) {
            throw new OkxApiException("convert rejected", CONVERT_ERROR);
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
            throw new OkxApiException("doConvert:" + e.getMessage(), CONVERT_ERROR);
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
            throw new OkxApiException("getConvertQuote:" + e.getMessage(), REQUIRE_QUOTE_ERROR);
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
            throw new OkxApiException("getConvertPair:" + e.getMessage(), REQUIRE_CONVERTPAIR_ERROR);
        }
        return convertCurrencyPair;
    }

    public boolean checkQuote(Client client, ConvertRequest convertRequest) {
        String mode = convertRequest.getMode();
        if (!("funding".equals(mode) || "trading".equals(mode) ||"both".equals(mode))) {
            throw new RuntimeException("交易模式错误");
        }

        double tradingBalance, amount, fundingBalance;

        tradingBalance = Double.parseDouble(accountService.getTradingBalance(client, convertRequest.getFromCcy()));
        fundingBalance = Double.parseDouble(accountService.getFundingBalance(client, convertRequest.getFromCcy()));
        amount = Double.parseDouble(convertRequest.getAmount());

        if ("funding".equals(mode)) {
            return fundingBalance >= amount;
        } else if ("trading".equals(mode)) {
            return tradingBalance >= amount;
        } else if ("both".equals(mode)){
            if (amount > fundingBalance + tradingBalance) {
                return false;
            }
            if (amount > fundingBalance) {
                Double transferAmount = amount - fundingBalance;
                accountService.tradingTransfer2Funding(client, String.valueOf(transferAmount), convertRequest.getFromCcy());
            }
        }else{
            throw new RuntimeException("不存在在 mode");
        }
        return true;
    }


}
