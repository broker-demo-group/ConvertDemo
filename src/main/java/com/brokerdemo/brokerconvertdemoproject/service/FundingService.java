package com.brokerdemo.brokerconvertdemoproject.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.constant.Constant;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.dto.request.TransferReqDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.request.WithdrawlReqDTO;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BusinessException;
import com.brokerdemo.brokerconvertdemoproject.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.constant.AccountTypeEnum;
import org.okxbrokerdemo.constant.TransferTypeEnum;
import org.okxbrokerdemo.handler.asset.QueryCurrencyRes;
import org.okxbrokerdemo.handler.funding.QueryBalanceRes;
import org.okxbrokerdemo.handler.funding.TransferReq;
import org.okxbrokerdemo.handler.funding.TransferRes;
import org.okxbrokerdemo.handler.funding.WithdrawalRes;
import org.okxbrokerdemo.handler.funding.WithdrawlReq;
import org.okxbrokerdemo.handler.subaccount.SubAccountTransferReq;
import org.okxbrokerdemo.handler.subaccount.SubAccountTransferRes;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FundingService {

    @Autowired
    private SubAccountRepository subAccountRepository;
    @Autowired
    private Client client;
    @Autowired
    private APIKeyHolder masterApiKeyHolder;

    public List<QueryBalanceRes> getAccountBalance(String username, String ccy) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(username);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        return client.getFunding().getBalance(ccy, UserUtil.generateAPIKeyHolder(subAccount));
    }


    public void withdrawal(WithdrawlReqDTO req, String subAcct) {
        if (Objects.isNull(req)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(subAcct);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }


        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().isSimluate(true).build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);
        // TODO 优化点，第一步和第二部的第一步接口调用设置为异步调用
        // 1、查询网络手续费 根据获取币种列表查询所得
        List<QueryCurrencyRes> currencies = client.getAsset().getCurrencies(req.getCcy(), apiKeyHolder);
        if (CollectionUtil.isEmpty(currencies)) {
            throw new BusinessException(BusinessExceptionEnum.QUERY_CURRENCY_ERROR);
        }

        QueryCurrencyRes queryCurrencyRes = currencies.get(Constant.ZERO);
        BigDecimal maxFee = new BigDecimal(queryCurrencyRes.getMaxFee());
        BigDecimal minFee = new BigDecimal(queryCurrencyRes.getMinFee());
        Double fee = maxFee.add(minFee).divide(new BigDecimal(Constant.TWO)).doubleValue();


        // 2、子账户转到母账户
        // TODO 如果提币失败需要做回滚操作
        TransferReqDTO transferReq = TransferReqDTO.builder()
                .ccy(req.getCcy())
                .amt(req.getAmt())
                .subAcct(subAcct)
                .from(AccountTypeEnum.FUNDING_ACCOUNT.getValue())
                .to(AccountTypeEnum.FUNDING_ACCOUNT.getValue())
                .type(TransferTypeEnum.SUB_ACCOUNT_TO_MASTER_ACCOUNT_FOR_MASTER_ACCOUNT.getType())
                .build();
        List<TransferRes> transferRes = transfer(transferReq);
        if (CollectionUtil.isEmpty(transferRes)) {
            throw new BusinessException(BusinessExceptionEnum.TRANSFER_ERROR);
        }

        // 3、提币到外部钱包
        WithdrawlReq withdrawlReq = WithdrawlReq.builder().fee(fee.toString()).build();
        BeanUtil.copyProperties(req, withdrawlReq);
        client.getFunding().withdrawal(withdrawlReq, masterApiKeyHolder);
    }

    public List<WithdrawalRes> withdrawal(WithdrawlReq req) {
        return client.getFunding().withdrawal(req, masterApiKeyHolder);
    }


    public List<TransferRes> transfer(TransferReqDTO req) {
        String transferType = req.getType();
        TransferTypeEnum transferTypeEnum = TransferTypeEnum.typeOf(transferType);
        if (Objects.isNull(transferTypeEnum)) {
            log.error("transfer fail,request:{}", req);
            throw new BusinessException(BusinessExceptionEnum.REQUEST_ERROR);
        }

        TransferReq transferReq = TransferReq.builder().build();
        BeanUtil.copyProperties(req, transferReq, false);
        return client.getFunding().assetTransfer(transferReq, getApikeyHolderByTransferType(transferTypeEnum, req.getSubAcct()));
    }

    public List<SubAccountTransferRes> subAccountTransfer(SubAccountTransferReq req) {
        return client.getSubAccount().subAccountTransfer(req, masterApiKeyHolder);
    }


    private APIKeyHolder getApikeyHolderByTransferType(TransferTypeEnum transferTypeEnum, String userName) {
        switch (transferTypeEnum) {
            case TRANSFER_WITHIN_ACCOUNT:
            case MASTER_ACCOUNT_TO_SUB_ACCOUNT:
            case SUB_ACCOUNT_TO_MASTER_ACCOUNT_FOR_MASTER_ACCOUNT:
                return masterApiKeyHolder;
            case SUB_ACCOUNT_TO_MASTER_ACCOUNT_FRO_SUB_ACCOUNT:
                // TODO 子传子需要设置主动转出权限 https://www.okx.com/docs-v5/zh/#rest-api-subaccount-set-permission-of-transfer-out
            case SUB_ACCOUNT_TO_SUB_ACCOUNT:
                SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
                if (Objects.isNull(subAccount)) {
                    throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
                }
                return UserUtil.generateAPIKeyHolder(subAccount);
            default:
                // never do it
                return null;
        }
    }
}
