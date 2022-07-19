package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        BrokerResponse brokerResponse = null;

        String token = request.getHeader("token");
        String username;
        if (StringUtils.hasText(token) && (username = cache.get(token)) !=null) {
            cache.remove(token);
            brokerResponse = new BrokerResponse(0, "\"logout success\"", "user:["+username + "] logout success");
        } else {
            brokerResponse = new BrokerResponse(1, "\"not login\"", "logout error:not login");
        }
        PrintWriter out = response.getWriter();
        out.println(brokerResponse.toString());
        out.flush();
        out.close();
    }
}