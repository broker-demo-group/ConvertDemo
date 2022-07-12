package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.cache.LocalCache;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import com.brokerdemo.brokerconvertdemoproject.utils.MongoUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author: bowen
 * @description:
 * @date: 2022/7/11  9:18 AM
 **/
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    Cache localCache;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        //直接输出json格式的响应信息
        Object principal = authentication.getPrincipal();
        String username = authentication.getName();
        String token = JwtTokenUtil.getToken(username);
        localCache.set(token,username);
        httpServletResponse.addHeader("token",token);
        PrintWriter out = httpServletResponse.getWriter();
        //以json格式对外输出身份信息
        System.out.println("创建token："+principal);
        System.out.println("创建token："+token);
//        out.write(new ObjectMapper().writeValueAsString(principal));
        out.println("onAuthenticationSuccess: login suc");
        out.flush();
        out.close();
        System.out.println("SuccessHandler"+httpServletRequest.getQueryString());
    }
}