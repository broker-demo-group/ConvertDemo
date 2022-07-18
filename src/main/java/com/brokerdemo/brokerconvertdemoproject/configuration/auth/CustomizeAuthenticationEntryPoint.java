package com.brokerdemo.brokerconvertdemoproject.configuration.auth;


import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
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
        BrokerResponse brokerResponse = new BrokerResponse(901,"\"Auth error\"",e.getMessage());
        httpServletResponse.getWriter().write(brokerResponse.toString());
    }
}
