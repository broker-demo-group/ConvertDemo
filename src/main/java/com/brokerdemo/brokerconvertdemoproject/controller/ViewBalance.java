package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.entity.Asset;
import com.brokerdemo.brokerconvertdemoproject.entity.BalanceEntity;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.UserBalanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;


/**
 * ROLE 用 "ROLE_USER"
 **/
@Controller
@ResponseBody
public class ViewBalance {
    @Autowired
    UserBalanceQuery userBalanceQuery;

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/viewAccountBalance")
    public BrokerResponse<List<BalanceEntity>> viewUserAccountBalance(@ApiIgnore Authentication authentication) throws IOException {
        String username = authentication.getName();
        return BrokerResponse.success(userBalanceQuery.accountBalanceQuery(username));
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/viewAssetBalance")
    public BrokerResponse<List<Asset>> viewAssetBalance(@ApiIgnore Authentication authentication) {
        String username = authentication.getName();
        return BrokerResponse.success(userBalanceQuery.assetBalanceQuery(username));
    }
}
