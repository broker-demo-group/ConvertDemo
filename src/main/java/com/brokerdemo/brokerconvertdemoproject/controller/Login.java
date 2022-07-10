package com.brokerdemo.brokerconvertdemoproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;
@RestController
public class Login {
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/api/restlogin")
    public Map<String,String> login(@RequestBody String username,@RequestBody String password){
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            Map<String,String> result = new HashMap<>();
            result.put("status","success");
            result.put("user",authenticate.getName());
            return result;
        }catch(BadCredentialsException ex){
            Map<String,String> result = new HashMap<>();
            result.put("status","failed");
            return result;
        }
    }



}
