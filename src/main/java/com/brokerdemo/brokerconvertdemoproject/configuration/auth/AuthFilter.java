package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.dto.request.LoginDTO;
import com.brokerdemo.brokerconvertdemoproject.utils.SecurityUtils;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @author: houyunlu
 * @date: 2022/8/8
 **/
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private Cache cache;

    private final Set<String> urlSet = Sets.newHashSet("/login", "/register", "/swagger-ui", "/swagger-resources",
            "/v2/api-docs", "/webjars/springfox-swagger-ui","/management");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (checkUri(urlSet, requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("token");
        if (StringUtils.isEmpty(authHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String cacheJson = cache.get(authHeader);
        if (StringUtils.isEmpty(cacheJson)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        LoginDTO login = new Gson().fromJson(cacheJson, LoginDTO.class);
        if (Objects.isNull(login)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request, response);
    }

    private boolean checkUri(Set<String> urlSet, String requestURI) {
        if (StringUtils.isEmpty(requestURI)) {
            return false;
        }

        for (String url : urlSet) {
            if (requestURI.startsWith(url)) {
                return true;
            }
        }

        return false;
    }
}
