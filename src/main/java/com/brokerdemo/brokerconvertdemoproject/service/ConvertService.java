package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.entity.ConvertPair;
import com.brokerdemo.brokerconvertdemoproject.entity.ConvertRequest;
import com.brokerdemo.brokerconvertdemoproject.entity.Quote;
import com.brokerdemo.brokerconvertdemoproject.entity.QuoteRequest;
import com.google.gson.JsonObject;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.exception.OkxApiException;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
//        ConvertRequest -> checkRequest -> getQuote -> doConvert

        Client client = accountService.getSubAccountClint(username);
        double transferAmount = checkRequest(client, convertRequest);
        Quote quote = getQuote(client, convertRequest);
        boolean isRollBack = false;
        if(transferAmount != 0){
            accountService.tradingTransfer2Funding(client, String.valueOf(transferAmount), convertRequest.getFromCcy());
            isRollBack = true;
        }
        try{
            doConvert(client, quote);
            isRollBack = false;
        }catch (OkxApiException e ){
            throw new OkxApiException("convert rejected", CONVERT_ERROR);
        }finally {
            //  若 doConvert 失败，且需要回滚，则isRollBack = true
            if(isRollBack){
                accountService.fundingTransfer2Trading(client, String.valueOf(transferAmount), convertRequest.getFromCcy());
            }
        }
    }

    /**
     * 根据报价 Quote 进行闪对交易
     *
     * @Return boolean 闪兑是否成功，若闪兑全部成功返回ture
     */
    public void doConvert(Client subAccountClient, Quote quote) {
        JsonObject tradeResult;
        try {
            ParamMap param = new ParamMap();
            param.add("quoteId", quote.getQuoteId());
            param.add("baseCcy", quote.getBaseCcy());
            param.add("quoteCcy", quote.getQuoteCcy());
            param.add("side", quote.getSide());
            param.add("sz", quote.getRfqSz());
            param.add("szCcy", quote.getRfqSzCcy());
            tradeResult = subAccountClient.getAssetConvert().convertTrade(param, JsonObject.class);
        } catch (OkxApiException e) {
            throw new OkxApiException("doConvert:" + e.getMessage(), CONVERT_ERROR);
        }

        tradeResult.get("code");
        String state = tradeResult.get("state").getAsString();
        if(!"fullyFilled".equals(state)){
            throw new OkxApiException("convert rejected", CONVERT_ERROR);
        }


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

    /**
     * 检查交易模式并返回交易账户需要划转到资金账户的金额
     */
    public double checkRequest(Client client, ConvertRequest convertRequest) {
        String mode = convertRequest.getMode();
        if (!("funding".equals(mode) || "trading".equals(mode) || "both".equals(mode))) {
            throw new RuntimeException("交易模式错误");
        }

        double tradingBalance, amount, fundingBalance;

        tradingBalance = Double.parseDouble(accountService.getTradingBalance(client, convertRequest.getFromCcy()));
        fundingBalance = Double.parseDouble(accountService.getFundingBalance(client, convertRequest.getFromCcy()));
        amount = Double.parseDouble(convertRequest.getAmount());

        if ("funding".equals(mode)) {
            if (fundingBalance < amount) {
                throw new OkxApiException("balance insufficient", CONVERT_ERROR);
            }
        } else if ("trading".equals(mode)) {
            if (tradingBalance < amount) {
                throw new OkxApiException("balance insufficient", CONVERT_ERROR);
            }
            return amount;
        } else {
            // mode = both
            if (amount > fundingBalance + tradingBalance) {
                throw new OkxApiException("balance insufficient", CONVERT_ERROR);
            }
            if (amount > fundingBalance) {
                return amount - fundingBalance;
            }
        }
        return 0;
    }


}
