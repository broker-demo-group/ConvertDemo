package com.brokerdemo.brokerconvertdemoproject.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.okxbrokerdemo.service.entry.ParamMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public String getStatus(@PathVariable String state) throws IOException {
        ParamMap paramMap = new ParamMap();
        paramMap.add("state", state);
        List<JsonObject> status = client.getStatus().getStatus(paramMap, JsonObject.class);
        return status.get(0).toString();
    }


}
