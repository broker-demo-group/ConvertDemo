package com.brokerdemo.brokerconvertdemoproject.controller.management;

import com.brokerdemo.brokerconvertdemoproject.dto.request.TransferReqDTO;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.BrokerService;
import com.brokerdemo.brokerconvertdemoproject.service.FundingService;
import org.okxbrokerdemo.handler.account.QueryAccountBalanceRes;
import org.okxbrokerdemo.handler.broker.QuerySubAccountListReq;
import org.okxbrokerdemo.handler.broker.QuerySubAccountListRes;
import org.okxbrokerdemo.handler.broker.SetTradingFeeRateReq;
import org.okxbrokerdemo.handler.broker.SetTradingFeeRateRes;
import org.okxbrokerdemo.handler.funding.TransferRes;
import org.okxbrokerdemo.handler.subaccount.SubAccountTransferReq;
import org.okxbrokerdemo.handler.subaccount.SubAccountTransferRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private BrokerService brokerService;
    @Autowired
    private FundingService fundingService;

    @GetMapping("/subaccount/list")
    public BrokerResponse<List<QuerySubAccountListRes>> getSubAccountList(QuerySubAccountListReq req) {
        return BrokerResponse.success(brokerService.getSubAccountList(req));
    }

    @GetMapping("/subaccount/balance")
    public BrokerResponse<List<QueryAccountBalanceRes>> getSubAccountBalance(@RequestParam("userName") String userName, @RequestParam(required = false) String ccy) {
        return BrokerResponse.success(brokerService.getSubAccountBalance(userName, ccy));
    }

    @PostMapping("/transfer")
    public BrokerResponse<List<TransferRes>> transfer(@RequestBody TransferReqDTO req) {
        return BrokerResponse.success(fundingService.transfer(req));
    }

    @PostMapping("/subaccount/transfer")
    public BrokerResponse<List<SubAccountTransferRes>> subAccountTransfer(@RequestBody SubAccountTransferReq req) {
        return BrokerResponse.success(fundingService.subAccountTransfer(req));
    }

    @PostMapping("/subaccount/fee_rate")
    public BrokerResponse<List<SetTradingFeeRateRes>> setSubAccountFeeRate(SetTradingFeeRateReq req) {
        return BrokerResponse.success(brokerService.setSubAccountFeeRate(req));
    }


    // subaccount List


    // OKX sub-account trading balance

    // Transfer from master to subaccount
    // from subaccount to master account
    // from subaccount to master account


}
