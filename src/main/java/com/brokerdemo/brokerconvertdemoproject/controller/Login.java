package com.brokerdemo.brokerconvertdemoproject.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class Login {
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/restlogin")
    public Map<String, String> login(@RequestBody Map<String, Object> paramsMap) {
        Gson gson = new Gson();
        log.info(gson.toJson(paramsMap));
        Map<String, String> result = new HashMap<>();
        if (authenticationManager == null) {
            result.put("status", "Auth ManagerNull");
            return result;
        }
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(paramsMap.get("username"), paramsMap.get("password"))
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            result.put("status", "success");
            result.put("user", authenticate.getName());
            return result;
        } catch (BadCredentialsException ex) {

            result.put("status", "failed");
            return result;
        }
    }


}
