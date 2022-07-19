package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.constant.BusinessExceptionEnum;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.NewUserCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController("/register")
public class RegisterController {
    @Autowired
    private NewUserCreator newUserCreator;

    @GetMapping
    public BrokerResponse register() {
        return BrokerResponse.success();
    }

    @PostMapping
    public BrokerResponse register(@RequestParam String userName, @RequestParam String passWord, @RequestParam String email,
                                   @RequestParam String firstName, @RequestParam String lastName) {
        User user = new User(userName, passWord, new Date(), false, firstName, lastName, email);
        boolean success = newUserCreator.createCommonUser(user);
        return success ? BrokerResponse.success() : BrokerResponse.error(BusinessExceptionEnum.CREATE_COMMON_USER_ERROR);
    }
}
