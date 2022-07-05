package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.service.UserBalanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;


/**
 *
 * ROLE 用 "ROLE_USER"
 * **/
@Controller
@ResponseBody
public class ViewBalance {
    @Autowired
    UserBalanceQuery userBalanceQuery;
    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/viewAccountBalance")
    public List viewUserAccountBalance(Principal principal) throws IOException {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userBalanceQuery.accountBalanceQuery(username);
    }
    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/viewAssetBalance")
    public List viewAssetBalance(Principal principal) throws IOException {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userBalanceQuery.assetBalanceQuery(username);
    }
}
