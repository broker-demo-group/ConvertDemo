package com.brokerdemo.brokerconvertdemoproject.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;


/**
 *
 * ROLE 用 "ROLE_USER"
 * **/
@Controller
@ResponseBody
public class ViewBalance {
    @GetMapping("/user/viewbalance")
    public Map<String,Object> showUserBalance(Principal principal){
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return null;
    }
}
