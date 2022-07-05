package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.entity.Quote;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "Convert Backend Api")
//@CrossOrigin(origins = "*")
@RestController
/**
 *
 * ROLE 用 "ROLE_USER"
 * **/
public class AssetConvert {

    @Resource
    Client client;
    @ApiOperation(value = "获取所有支持闪兑的虚拟货币列表", notes = "some notes ")
    @GetMapping(value = "/asset/convert/currencies")
    public String getCurrencies() {
        log.info("/asset/convert/currencies");
        return client.getAssetConvert().getConvertCurrencies(new ParamMap(), JsonObject.class).toString();
    }

    @PostMapping(value = "asset/convert/estimate-quote")
    public String getQuote(@RequestBody Quote quote) {
        return client.getAssetConvert().convertEstimateQuote(quote, JsonObject.class).toString();
    }
}
