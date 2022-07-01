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

import javax.annotation.security.RolesAllowed;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.tomcat.jni.Time.now;

@Controller
public class Register {
    @Autowired
    NewUserCreator newUserCreator;

    @GetMapping("/register")
    public String register(){
        return "Register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String userName,@RequestParam String passWord,@RequestParam String email,@RequestParam String firstName,@RequestParam String lastName){

        User user = new User(userName,passWord, new Date(now()),false,firstName,lastName,email);
        if(newUserCreator.createCommonUser(user)){
            return "Success";
        }
        return "Error!";

    }
}
