package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BusinessException;
import com.brokerdemo.brokerconvertdemoproject.utils.LRUCache;
import com.brokerdemo.brokerconvertdemoproject.utils.UserUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.handler.funding.QueryBalanceRes;
import org.okxbrokerdemo.handler.funding.TransferReq;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    @Value("${broker.api.isSimulate}")
    boolean isSimulate;
    @Resource
    SubAccountRepository subAccountRepository;
    @Autowired
    LRUCache<String, Client> lruCache;
    @Autowired
    private Client client;

    public List<QueryBalanceRes> getAccountBalance(String username, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(username);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        return client.getFunding().getBalance(ccy, UserUtil.generateAPIKeyHolder(subAccount));
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
