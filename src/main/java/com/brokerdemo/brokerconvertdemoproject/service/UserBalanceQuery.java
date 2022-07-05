package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.ApiParam;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.Asset;
import com.brokerdemo.brokerconvertdemoproject.entity.BalanceEntity;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBalanceQuery {
    @Autowired
    SubAccountRepository subAcuntRepository;


    public List<Asset> assetBalanceQuery(String userName){

        SubAccount subAccount = subAcuntRepository.findSubAccountByUserName(userName);
        Client client = OkxSDK.getClient(subAccount.getApiKey(),subAccount.getApiSecret(),subAccount.getPassphrase(),false);
        ApiParam param = new ApiParam();
        List<Asset> assetList = client.getAsset().getAssetBalance(param, Asset.class);
        return assetList;
    }



    public List<BalanceEntity> accountBalanceQuery(String userName) throws IOException {
        SubAccount subAccount = subAcuntRepository.findSubAccountByUserName(userName);
        Client client = OkxSDK.getClient(subAccount.getApiKey(),subAccount.getApiSecret(),subAccount.getPassphrase(),false);
        ApiParam param = new ApiParam();
        List<BalanceEntity> balanceEntities = client.getAccount().getBalance(param,BalanceEntity.class);
        return balanceEntities;
    }

}
