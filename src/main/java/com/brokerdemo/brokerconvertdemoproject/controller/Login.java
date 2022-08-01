package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.dto.request.LoginDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.LoginResDTO;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import com.brokerdemo.brokerconvertdemoproject.utils.SecurityUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Login {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private Cache cache;

    @PostMapping("/login")
    public BrokerResponse<LoginResDTO> login(@RequestBody LoginDTO loginReq) {
        Authentication authToken = SecurityUtils.login(loginReq.getUserName(), loginReq.getPassword(), authenticationManager);
        String token = JwtTokenUtil.getToken(authToken.getName());
        cache.set(token, new Gson().toJson(loginReq));
        return BrokerResponse.success(LoginResDTO.builder().token(token).build());
    }


}
