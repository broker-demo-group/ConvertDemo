package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.handler.broker.CreatSubAccountDepositAddressRes;
import org.okxbrokerdemo.handler.broker.CreateSubAccountDepositeAddressReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountDepositAddressReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountDepositAddressRes;
import org.okxbrokerdemo.handler.funding.WithdrawalRes;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class BrokerService {

    @Autowired
    private Client client;
    @Autowired
    private APIKeyHolder apiKeyHolder;
    @Autowired
    private SubAccountRepository subAccountRepository;


    public List<QuerySubAccountDepositAddressRes> getSubAccountDepositAddress(String userName, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
        if (Objects.isNull(subAccount)) {
            return null;
        }

        QuerySubAccountDepositAddressReq req = QuerySubAccountDepositAddressReq.builder().subAcct(subAccount.getSubAccountName()).ccy(ccy).build();
        return client.getBroker().getSubAccountDepositAddress(req, apiKeyHolder);
    }

    public List<CreatSubAccountDepositAddressRes> createSubAccountDepositAddress(String userName, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
        if (Objects.isNull(subAccount)) {
            return null;
        }

        CreateSubAccountDepositeAddressReq req = CreateSubAccountDepositeAddressReq.builder().subAcct(subAccount.getSubAccountName()).ccy(ccy).build();
        return client.getBroker().createSubAccountDepositAddress(req, apiKeyHolder);
    }

}
