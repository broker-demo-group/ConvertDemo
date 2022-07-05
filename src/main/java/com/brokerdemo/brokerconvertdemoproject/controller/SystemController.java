package com.brokerdemo.brokerconvertdemoproject.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author: bowen
 * @description:
 * @date: 2022/6/29  7:18 AM
 **/

@Api(tags = "SystemStatus Backend Api")
@Slf4j
@RestController
public class SystemController {

    @Resource
    Client client;

    @GetMapping("system/status/{state}")
    @ApiOperation(value = "获取 okx 系统状态", notes = "notes")
    @ApiImplicitParams({@ApiImplicitParam(name = "state", value = "state", required = true, paramType = "path", example = "canceled")})
    public String getStatus(@PathVariable String state) {
        ParamMap paramMap = new ParamMap();
        paramMap.add("state", state);
        List<JsonObject> status = client.getStatus().getStatus(paramMap, JsonObject.class);
        return status.get(0).toString();
    }


}
