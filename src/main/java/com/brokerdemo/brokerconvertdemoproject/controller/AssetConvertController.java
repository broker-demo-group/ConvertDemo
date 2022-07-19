package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.entity.ConvertRequest;
import com.brokerdemo.brokerconvertdemoproject.entity.QuoteRequest;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.AccountService;
import com.brokerdemo.brokerconvertdemoproject.service.ConvertService;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  10:14 PM
 **/
@Slf4j
@Api(tags = "Convert Backend Api")
@CrossOrigin(origins = "*")
@RestController("/asset/convert")
/**
 *
 * ROLE 用 "ROLE_USER"
 * **/
public class AssetConvertController {

    @Resource
    Client client;

    @Resource
    ConvertService convertService;
    @Resource
    AccountService accountService;

    @ApiOperation(value = "获取所有支持闪兑的虚拟货币列表")
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/currencies")
    public BrokerResponse getCurrencies() {
        return BrokerResponse.success(client.getAssetConvert().getConvertCurrencies(new ParamMap(), JsonObject.class).toString());
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping(value = "/estimate-quote")
    public BrokerResponse getQuote(@RequestBody QuoteRequest quoteRequest, @ApiIgnore Authentication authentication) {
        String username = authentication.getName();

        Client subAccountClint = accountService.getSubAccountClint(username);
        String data = convertService.getQuote(subAccountClint, quoteRequest).toString();

        return BrokerResponse.success(data);
    }

    @PostMapping(value = "/trade")
    @RolesAllowed("ROLE_USER")
    public BrokerResponse convertTrade(@RequestBody ConvertRequest convertRequest, @ApiIgnore Authentication authentication) {
        String username = authentication.getName();

        convertService.convert(convertRequest, username);
        return BrokerResponse.success();
    }


}
