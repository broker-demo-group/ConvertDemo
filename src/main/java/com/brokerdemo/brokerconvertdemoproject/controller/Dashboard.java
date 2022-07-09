package com.brokerdemo.brokerconvertdemoproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Dashboard {
    @RolesAllowed("ROLE_USER")
    @GetMapping("/dashboard")
    public Map<String,String> loginSuccess( @ApiIgnore Authentication authentication){
        Map<String,String> result = new HashMap<>();
        result.put("status","success");
        result.put("user",authentication.getName());
        return result;
    }

}
