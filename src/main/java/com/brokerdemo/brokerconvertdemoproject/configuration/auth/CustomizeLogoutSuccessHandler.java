package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    Cache cache;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        PrintWriter out = response.getWriter();
        BrokerResponse brokerResponse = new BrokerResponse(0,"","logout success");
        out.println(brokerResponse.toString());
        out.flush();
        out.close();


    }
}