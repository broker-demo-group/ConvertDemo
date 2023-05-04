package com.brokerdemo.brokerconvertdemoproject.service;

import cn.hutool.core.bean.BeanUtil;
import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.dao.domain.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.handler.account.QueryAccountBalanceRes;
import org.okxbrokerdemo.handler.broker.CreatSubAccountDepositAddressRes;
import org.okxbrokerdemo.handler.broker.CreateSubAccountDepositeAddressReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountDepositAddressReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountDepositAddressRes;
import org.okxbrokerdemo.handler.broker.QuerySubAccountListReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountListRes;
import org.okxbrokerdemo.handler.broker.SetTradingFeeRateReq;
import org.okxbrokerdemo.handler.broker.SetTradingFeeRateRes;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BrokerService {

    @Autowired
    private Client client;
    @Autowired
    private APIKeyHolder masterApiKeyHolder;
    @Autowired
    private SubAccountRepository subAccountRepository;


    public List<QuerySubAccountDepositAddressRes> getSubAccountDepositAddress(String userName, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByBelongUserId(userName);
        if (Objects.isNull(subAccount)) {
            return null;
        }

        QuerySubAccountDepositAddressReq req = QuerySubAccountDepositAddressReq.builder().subAcct(subAccount.getSubAccountName()).ccy(ccy).build();
        return client.getBroker().getSubAccountDepositAddress(req, masterApiKeyHolder);
    }

    public List<CreatSubAccountDepositAddressRes> createSubAccountDepositAddress(String userName, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByBelongUserId(userName);
        if (Objects.isNull(subAccount)) {
            return null;
        }

        CreateSubAccountDepositeAddressReq req = CreateSubAccountDepositeAddressReq.builder().subAcct(subAccount.getSubAccountName()).ccy(ccy).build();
        return client.getBroker().createSubAccountDepositAddress(req, masterApiKeyHolder);
    }

    public List<QuerySubAccountListRes> getSubAccountList(QuerySubAccountListReq req) {
        return client.getBroker().getSubAccountList(req, masterApiKeyHolder);

    }

    public List<QueryAccountBalanceRes> getSubAccountBalance(String userName, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByBelongUserId(userName);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().isSimluate(true).build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);

        return client.getAccount().getBalance(ccy, apiKeyHolder);
    }

    public List<SetTradingFeeRateRes> setSubAccountFeeRate(SetTradingFeeRateReq req){
        return client.getBroker().setSubAccountFeeRate(req,masterApiKeyHolder);
    }
}
