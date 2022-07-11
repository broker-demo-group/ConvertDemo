package com.brokerdemo.brokerconvertdemoproject.configuration.auth;


import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author: bowen
 * @description:
 * @date: 2022/7/11  9:17 AM
 **/
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        JsonObject result = new JsonObject();
        result.add("msg",new JsonPrimitive("login in"));
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(result.toString());
    }
}
