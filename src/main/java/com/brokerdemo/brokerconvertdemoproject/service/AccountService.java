package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BrokerApiException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoException;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
@Service
public class AccountService {
    @Value("${broker.api.isSimulate}")
    boolean isSimulate;
    @Resource
    SubAccountRepository subAccountRepository;

    public String getAccountBalance(String username,String ccy) throws IOException {
        ParamMap param = new ParamMap();
        param.add("ccy", ccy);
        Client client = getSubAccountClint(username);
        JsonObject fundingBalance = client.getAsset().getAssetBalance(param, JsonObject.class).get(0);
        JsonObject tradingBalance = client.getAccount().getBalance(param,JsonObject.class).get(0);
        JsonObject result = new JsonObject();
        JsonArray details = tradingBalance.get("details").getAsJsonArray();
        String trading;
        if(details.size() == 0){
            trading = "0";
        }else{
            trading = details.get(0).getAsJsonObject().get("availBal").getAsString();
        }
        result.addProperty("funding",fundingBalance.get("availBal").getAsString());
        result.addProperty("trading", trading);
        return result.toString();
    }

//    @Resource
    Client client;
    public Client getSubAccountClint(String username) {
        // test
//        return client;

        SubAccount subAccount;
        subAccount = subAccountRepository.findSubAccountByUserName(username);
        return OkxSDK.getClient(subAccount.getApiKey(),subAccount.getApiSecret(),subAccount.getPassphrase(),isSimulate);
    }
}
