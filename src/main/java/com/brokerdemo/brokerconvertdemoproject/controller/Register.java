package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.cache.LocalCache;
import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.dto.request.RegisterDTO;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class Register {
    @Autowired
    private UserService newUserCreator;

    @PostMapping("/register")
    @ResponseBody
    public BrokerResponse register(@RequestBody RegisterDTO register) {
        User user = new User(register.getUsername(), register.getPassword(), new Date(), false,
                register.getFirstName(), register.getLastName(), register.getEmail());
        newUserCreator.createCommonUser(user);
        return BrokerResponse.success();
    }
}
