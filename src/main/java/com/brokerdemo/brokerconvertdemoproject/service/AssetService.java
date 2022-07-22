package com.brokerdemo.brokerconvertdemoproject.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.constant.Constant;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.handler.Asset.QueryCurrencyRes;
import org.okxbrokerdemo.handler.SubAccount.SetTransOut;
import org.okxbrokerdemo.handler.funding.TransferReq;
import org.okxbrokerdemo.handler.funding.WithdrawlReq;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AssetService {

    @Autowired
    private Client client;
    @Autowired
    private APIKeyHolder parentApiKeyHolder;
    @Autowired
    private SubAccountRepository subAccountRepository;

    public void withdrawal(String ccy, String amt, String dest, String toAddr, String userName) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().isSimluate(true).build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);
        // TODO 优化点，第一步和第二部的第一步接口调用设置为异步调用
        // 1、查询网络手续费 根据获取币种列表查询所得
        List<QueryCurrencyRes> currencies = client.getAsset().getCurrencies(ccy, apiKeyHolder);
        if (CollectionUtil.isEmpty(currencies)) {
            throw new BusinessException(BusinessExceptionEnum.QUERY_CURRENCY_ERROR);
        }

        QueryCurrencyRes queryCurrencyRes = currencies.get(Constant.ZERO);
        BigDecimal maxFee = new BigDecimal(queryCurrencyRes.getMaxFee());
        BigDecimal minFee = new BigDecimal(queryCurrencyRes.getMinFee());
        Double fee = maxFee.add(minFee).divide(new BigDecimal(Constant.TWO)).doubleValue();


        // 2、子账户转到母账户
        // 分两步 第一步，设置子账户权限；第二部，转钱到母账户
        SetTransOut setTransOut = SetTransOut.builder()
                .subAcct(subAccount.getSubAccountName())
                .canTransOut(Boolean.TRUE)
                .build();
        List<SetTransOut> setTransOuts = client.getSubAccount().setTransferOut(setTransOut, apiKeyHolder);
        if (CollectionUtil.isEmpty(setTransOuts)) {
            throw new BusinessException(BusinessExceptionEnum.SET_TRANS_OUT_ERROR);
        }

        TransferReq req = TransferReq.builder()
                .ccy(ccy)
                .amt(amt)
                .type("2")
                .from("6")
                .to("6")
                .build();
        List<TransferReq> transferReqs = client.getAsset().assetTransfer(req, apiKeyHolder);
        if (CollectionUtil.isEmpty(transferReqs)) {
            throw new BusinessException(BusinessExceptionEnum.TRANSFER_ERROR);
        }

        // 3、提币到外部钱包
        // TODO 这里是分布式事务处理场景，需要做优化
        WithdrawlReq withdrawlReq = WithdrawlReq.builder()
                .ccy(ccy)
                .dest(dest)
                .toAddr(toAddr)
                .fee(fee.toString())
                .build();
        client.getAsset().withdrawal(withdrawlReq, parentApiKeyHolder);
    }
}
