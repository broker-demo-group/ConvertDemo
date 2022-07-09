package com.brokerdemo.brokerconvertdemoproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.util.Map;
@Controller
public class Login {
    @RolesAllowed("ROLE_USER")
    @GetMapping("/loginforward")
    public String loginSuccess(Authentication authentication, String url){

    }
}
