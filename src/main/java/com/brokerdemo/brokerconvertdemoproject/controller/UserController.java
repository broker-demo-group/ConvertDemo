package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.dto.request.LoginDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.request.RegisterDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.LoginResDTO;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.UserService;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import com.brokerdemo.brokerconvertdemoproject.utils.SecurityUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController("/user")
public class UserController {
    @Autowired
    private UserService newUserCreator;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private Cache cache;

    @PostMapping("/register")
    @ResponseBody
    public BrokerResponse register(@RequestBody RegisterDTO register) {
        User user = new User(register.getUsername(), register.getPassword(), new Date(), false,
                register.getFirstName(), register.getLastName(), register.getEmail());
        newUserCreator.createCommonUser(user);
        return BrokerResponse.success();
    }

    @PostMapping("/login")
    public BrokerResponse<LoginResDTO> login(@RequestBody LoginDTO loginReq) {
        Authentication authToken = SecurityUtils.login(loginReq.getUsername(), loginReq.getPassword(), authenticationManager);
        String token = JwtTokenUtil.getToken(authToken.getName());
        cache.set(token, new Gson().toJson(loginReq));
        return BrokerResponse.success(LoginResDTO.builder().token(token).build());
    }
}
