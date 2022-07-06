package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.service.NewUserCreator;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import java.time.Instant;
import java.util.*;

import static org.apache.tomcat.jni.Time.now;

@Controller
public class Register {
    @Autowired
    private NewUserCreator newUserCreator;



    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String,Object> register(@RequestParam String userName, @RequestParam String passWord, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName){
        User user = new User(userName,passWord, new Date(now()),false,firstName,lastName,email);
        HashMap<String,Object> resultMap = new HashMap<>();
        if(newUserCreator.createCommonUser(user)){
            resultMap.put("status","success");
            return resultMap;
        }
        resultMap.put("status","error");
        return resultMap;
    }
}
