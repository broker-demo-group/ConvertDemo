package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.UserBalanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.security.Principal;


/**
 * ROLE 用 "ROLE_USER"
 **/
@RestController("/user")
public class ViewBalanceController {
    @Autowired
    UserBalanceQuery userBalanceQuery;

    @RolesAllowed("ROLE_USER")
    @GetMapping("/viewAccountBalance")
    public BrokerResponse viewUserAccountBalance(Principal principal) throws IOException {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return BrokerResponse.success(userBalanceQuery.accountBalanceQuery(username));
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/viewAssetBalance")
    public BrokerResponse viewAssetBalance(Principal principal) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return BrokerResponse.success(userBalanceQuery.assetBalanceQuery(username));
    }
}
