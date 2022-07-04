package com.brokerdemo.brokerconvertdemoproject.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  7:19 AM
 **/
@Slf4j
@Api(tags = "Asset Backend Api")
@RestController
@CrossOrigin(origins = "*",allowCredentials = "true", allowedHeaders = "*")
public class Asset {
    @Resource
    Client client;
    @ApiOperation(value = "获取所有币的信息和图标的url",notes="some notes ")
    @GetMapping(value = "/asset/currencies")
    public String getCurrencies() {
        log.info("/asset/currencies");
        return client.getAsset().getCurrencies(new ParamMap(), JsonObject.class).toString();
    }

    @ApiOperation(value = "查询账户资产",notes="some notes ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ccy",
                    value = "查询币种",
                    example = "BTC"
            )
    })
    @GetMapping(value = "/asset/balances")
    public String getAssetBalances( @RequestParam(value = "ccy") String ccy){
        log.info("GET /api/v5/asset/balances/{}",ccy);
        ParamMap param = new ParamMap();
        param.add("ccy",ccy);
        System.out.println("payload"+param.getPayLoadJson());
        return client.getAsset().getAssetBalance(param,JsonObject.class).toString();
    }
}
