package com.brokerdemo.brokerconvertdemoproject.service;

import cn.hutool.core.bean.BeanUtil;
import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.handler.trade.PlaceOrderReq;
import org.okxbrokerdemo.handler.trade.PlaceOrderRes;
import org.okxbrokerdemo.handler.trade.QueryOrderDetailReq;
import org.okxbrokerdemo.handler.trade.QueryOrderDetailRes;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TradeService {

    @Autowired
    private Client client;
    @Autowired
    private SubAccountRepository subAccountRepository;

    public List<QueryOrderDetailRes> getOrder(QueryOrderDetailReq req, String userName) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);
        return client.getTrade().getOrder(req, apiKeyHolder);
    }

    public List<PlaceOrderRes> placeOrder(PlaceOrderReq req, String userName) {
        SubAccount subAccount = subAccountRepository.findSubAccountByUserName(userName);
        if (Objects.isNull(subAccount)) {
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST);
        }

        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);
        return client.getTrade().placeOrder(req, apiKeyHolder);
    }
}
