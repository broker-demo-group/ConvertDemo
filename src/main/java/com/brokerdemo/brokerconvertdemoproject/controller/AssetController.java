package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.AccountService;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  7:19 AM
 **/
@Slf4j
@Api(tags = "Asset Backend Api")
@RestController
@CrossOrigin(origins = "*")
public class AssetController {
    @Resource
    Client client;

    @Resource
    AccountService accountService;

    @ApiOperation(value = "获取所有币的信息和图标的url")
    @GetMapping(value = "/asset/currencies")
    public BrokerResponse getCurrencies() {
        return BrokerResponse.success(client.getAsset().getCurrencies(new ParamMap(), JsonObject.class).toString());
    }

    @ApiOperation(value = "查询账户资产")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccy",
                    value = "查询币种",
                    example = "BTC"
            )
    })
    @GetMapping(value = "/asset/balances")
    @RolesAllowed("ROLE_USER")
    public BrokerResponse getAssetBalances(@RequestParam(value = "ccy") String ccy, @ApiIgnore Authentication authentication) {
        String username = authentication.getName();
        String data = accountService.getAccountBalance(username, ccy);
        return BrokerResponse.success(data);
    }

    @ApiOperation(value = "资金账户与交易账户资金转移 [1:funding to trading] [2:trading to funding]", notes = "some notes ")
    @GetMapping(value = "/asset/transfer")
    @RolesAllowed("ROLE_USER")
    public BrokerResponse transfer(@RequestParam(value = "mode") String mode,
                                   @RequestParam(value = "ccy") String ccy,
                                   @RequestParam(value = "amount") String amount,
                                   @ApiIgnore Authentication authentication) {
        int i = Integer.parseInt(mode);
        String username = authentication.getName();
        Client subAccountClint = accountService.getSubAccountClint(username);
        if (i == 1) {
            accountService.fundingTransfer2Trading(subAccountClint, amount, ccy);
        }
        if (i == 2) {
            accountService.tradingTransfer2Funding(subAccountClint, amount, ccy);
        }
        return BrokerResponse.success();
    }
}
