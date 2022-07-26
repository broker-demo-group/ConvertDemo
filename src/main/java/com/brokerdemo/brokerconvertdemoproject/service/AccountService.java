package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.utils.LRUCache;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.handler.funding.TransferReq;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AccountService {
    @Value("${broker.api.isSimulate}")
    boolean isSimulate;
    @Resource
    SubAccountRepository subAccountRepository;
    @Autowired
    LRUCache<String, Client> lruCache;

    public String getAccountBalance(String username, String ccy) {
        ParamMap param = new ParamMap();
        param.add("ccy", ccy);
        Client client = getSubAccountClint(username);
        JsonObject fundingBalance = client.getAsset().getAssetBalance(param, JsonObject.class).get(0);
        JsonObject tradingBalance = client.getAccount().getBalance(param, JsonObject.class).get(0);
        JsonObject result = new JsonObject();
        JsonArray details = tradingBalance.get("details").getAsJsonArray();
        String trading;
        if (details.size() == 0) {
            trading = "0";
        } else {
            trading = details.get(0).getAsJsonObject().get("availBal").getAsString();
        }
        result.addProperty("funding", fundingBalance.get("availBal").getAsString());
        result.addProperty("trading", trading);
        return result.toString();
    }

    public String getFundingBalance(Client client, String ccy) {
        ParamMap paramMap = new ParamMap();
        paramMap.add("ccy", ccy);
        List<JsonObject> assetBalance = client.getAsset().getAssetBalance(paramMap, JsonObject.class);
        if (assetBalance.size() == 0) {
            return "0";
        }
        return assetBalance.get(0).get("availBal").getAsString();
    }

    public String getTradingBalance(Client client, String ccy) {
        String tradingBalance;
        ParamMap paramMap = new ParamMap();
        paramMap.add("cyy",ccy);
        JsonArray jsonArray =
                client.getAccount().getBalance(paramMap, JsonObject.class).get(0).get("details").getAsJsonArray();
        if(jsonArray.size()==0){
            tradingBalance = "0";
        }else{
            tradingBalance = jsonArray.get(0).getAsJsonObject().get("availBal").getAsString();
        }
        return tradingBalance;
    }

    public void tradingTransfer2Funding(Client client, String amount, String ccy) {
        this.doTransfer(client, amount, ccy, "18", "6");
    }

    public void fundingTransfer2Trading(Client client, String amount, String ccy) {
        this.doTransfer(client, amount, ccy, "6", "18");
    }

    public void doTransfer(Client client, String amount, String ccy, String from, String to) {
        ParamMap param = new ParamMap();
        param.add("ccy", ccy);
        param.add("amt", amount);
        param.add("from", from); // 6:funding account 18: trading account
        param.add("to", to);

        TransferReq req = TransferReq.builder()
                .ccy(ccy)
                .amt(amount)
                .from(from)
                .to(to)
                .build();
        client.getAsset().assetTransfer(req, client.getApiKeyHolder());
    }

    public Client getSubAccountClint(String username) {
        SubAccount subAccount;
        subAccount = subAccountRepository.findSubAccountByUserName(username);
        if (lruCache.getValue(username) != null) {
            return lruCache.getValue(username);
        }
        Client subClient = OkxSDK.getClient(subAccount.getApiKey(), subAccount.getApiSecret(), subAccount.getPassphrase(), isSimulate);
        lruCache.putValue(username, subClient);
        return subClient;
    }
}
