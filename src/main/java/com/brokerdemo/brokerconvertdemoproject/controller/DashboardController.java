package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DashboardController {
    @RolesAllowed("ROLE_USER")
    @GetMapping("/dashboard")
    public BrokerResponse loginSuccess(@ApiIgnore Authentication authentication){
        // TODO 确认怎么修改 houyunlu
        Map<String,String> result = new HashMap<>();
        result.put("status","success");
        result.put("user",authentication.getName());
        return BrokerResponse.success();
    }

}
