package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.NewUserCreator;
import io.swagger.annotations.ApiOperation;
import org.okxbrokerdemo.exception.OkxApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class Register {
    @Autowired
    NewUserCreator newUserCreator;
    @ApiOperation(value = "测试，返回Register", notes = "some notes ")
    @GetMapping("/register")
    public String register(){
        return "Register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam String userName, @RequestParam String passWord, @RequestParam String email,
                      @RequestParam String firstName, @RequestParam String lastName){
        User user = new User(userName,passWord, new Date(),false,firstName,lastName,email);
        HashMap<String,Object> resultMap = new HashMap<>();
        try {
            newUserCreator.createCommonUser(user);
        }catch (OkxApiException e){
            return new BrokerResponse(1,"",e.getMessage()).toString();
        }
        return  new BrokerResponse().toString();
    }
}
