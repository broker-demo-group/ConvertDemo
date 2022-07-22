package com.brokerdemo.brokerconvertdemoproject.controller;


import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.BrokerService;
import com.google.gson.Gson;
import org.okxbrokerdemo.handler.broker.CreatSubAccountDepositAddressRes;
import org.okxbrokerdemo.handler.broker.QuerySubAccountDepositAddressRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

@RestController
@RequestMapping("/broker")
public class BrokerController {

    @Autowired
    private BrokerService brokerService;


    @GetMapping("/nd/subaccount-deposit-address")
    public BrokerResponse getSubAccountDepositAddress(@RequestParam(value = "ccy") String ccy,
                                                      @ApiIgnore Authentication authentication) {
        List<QuerySubAccountDepositAddressRes> subAccountDepositAddress = brokerService.getSubAccountDepositAddress(authentication.getName(), ccy);
        return BrokerResponse.success(new Gson().toJson(subAccountDepositAddress));
    }

    @PostMapping("/nd/subaccount-deposit-address")
    public BrokerResponse createSubAccountDepositAddress(@RequestParam(value = "ccy") String ccy,
                                                         @ApiIgnore Authentication authentication) {
        List<CreatSubAccountDepositAddressRes> creatSubAccountDepositAddressRes = brokerService.createSubAccountDepositAddress(authentication.getName(), ccy);

        return BrokerResponse.success(new Gson().toJson(creatSubAccountDepositAddressRes));
    }
}
