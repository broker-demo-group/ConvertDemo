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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.brokerdemo.brokerconvertdemoproject.controller.advice.ErrorCode.UNDEFINED_ERROR;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  7:19 AM
 **/
@Slf4j
@Api(tags = "Asset Backend Api")
@RestController
@CrossOrigin(origins = "*")
public class Asset {
    @Resource
    Client client;

    @Resource
    AccountService accountService;

    @ApiOperation(value = "获取所有币的信息和图标的url", notes = "some notes ")
    @GetMapping(value = "/asset/currencies")
    public String getCurrencies() {
        log.info("/asset/currencies");
        String data = client.getAsset().getCurrencies(new ParamMap(), JsonObject.class).toString();
        return new BrokerResponse(0, data, "").toString();
    }

    @ApiOperation(value = "查询账户资产", notes = "some notes ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccy",
                    value = "查询币种",
                    example = "BTC"
            )
    })
    @GetMapping(value = "/asset/balances")
    @RolesAllowed("ROLE_USER")
    public String getAssetBalances(@RequestParam(value = "ccy") String ccy, @ApiIgnore Authentication authentication) throws IOException {
        String username = authentication.getName();
        log.info("GET /api/v5/asset/balances/{}  user:{}", ccy,username);
        String data;
        try {
            data = accountService.getAccountBalance(username, ccy);
        }catch (RuntimeException e){
            return new BrokerResponse(UNDEFINED_ERROR,"","未知错误").toString();
        }


        return new BrokerResponse(0, data, "").toString();
    }
}
