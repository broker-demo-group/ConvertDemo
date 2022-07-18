package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
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
public class CustomizeAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    /**
     * AuthenticationException:异常信息
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        //直接输出json格式的响应信息
        response.setContentType("application/json;charset=utf-8");
        new BrokerResponse(101,"",e.getMessage());
        PrintWriter out = response.getWriter();
        out.write(e.getMessage());
        out.flush();
        out.close();
        System.out.println("FailureHandler"+request.getQueryString());

    }
}