package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
        localCache.set(token, username);
        httpServletResponse.addHeader("token", token);
        PrintWriter out = httpServletResponse.getWriter();
        //以json格式对外输出身份信息
        System.out.println("创建token：" + principal);
        System.out.println("创建token：" + token);
        BrokerResponse brokerResponse = new BrokerResponse(0, "\""+token+"\"", username + " login success");
        out.println(brokerResponse.toString());
        out.flush();
        out.close();
        System.out.println("SuccessHandler" + httpServletRequest.getQueryString());
    }
}