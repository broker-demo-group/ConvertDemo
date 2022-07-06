package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.entity.QuoteRequest;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.ConvertService;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.exception.OkxApiException;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  10:14 PM
 **/
@Slf4j
@Api(tags = "Convert Backend Api")
@CrossOrigin(origins = "*")
@RestController
/**
 *
 * ROLE 用 "ROLE_USER"
 * **/
public class AssetConvert {

    @Resource
    Client client;

    @Resource
    ConvertService convertService;

    @ApiOperation(value = "获取所有支持闪兑的虚拟货币列表", notes = "some notes ")
    @GetMapping(value = "/asset/convert/currencies")
    @RolesAllowed("ROLE_USER")
    @ResponseBody
    public String getCurrencies(Principal principal) {

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("/asset/convert/currencies");
        String data = client.getAssetConvert().getConvertCurrencies(new ParamMap(), JsonObject.class).toString();
        return new BrokerResponse(0,data, "").toString();
    }
    @RolesAllowed("ROLE_USER")
    @PostMapping(value = "asset/convert/estimate-quote")
    @ResponseBody
    public String getQuote(@RequestBody QuoteRequest quoteRequest, Principal principal) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        String data = convertService.getQuote(client,quoteRequest).toString();
        return new BrokerResponse(0,data,"").toString();
    }

    @PostMapping(value = "asset/convert/trade")
    @ResponseBody
    public String convertTrade(@RequestBody QuoteRequest quoteRequest,Principal principal) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        try{
            convertService.convert(quoteRequest);
        }catch (OkxApiException e){
            return new BrokerResponse(e.getCode(),"",e.getMessage()).toString();
        }
        return new BrokerResponse().toString();
    }


}
