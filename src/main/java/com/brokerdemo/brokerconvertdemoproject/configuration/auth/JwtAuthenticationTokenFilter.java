package com.brokerdemo.brokerconvertdemoproject.configuration.auth;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.cache.LocalCache;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import com.brokerdemo.brokerconvertdemoproject.utils.MongoUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author: bowen
 * @description:
 * @date: 2022/7/11  9:18 AM
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserDetailsService UserDetailsService;
    @Autowired
    Cache localCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        // token 是否过期
        // TODO Localcache 也有一个过期时间，保持一致或只保留一个过期策略
        // 解析token
        if(JwtTokenUtil.isTokenExpired(token)){
            throw new RuntimeException("令牌已过期，请重新登录。");
        }

        // 从 LocalCache 中获取 username
        String username = localCache.get(token);

        // 从 token 中获取 username
        //        String username = JwtTokenUtil.getUsername(token);
        if(!StringUtils.hasText(username)){
            throw new RuntimeException("令牌已失效，请重新登录。");
        }
        UserDetails userDetails = UserDetailsService.loadUserByUsername(username);


        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 存入 SecurityContextHolder 用于后续鉴权
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 放行
        filterChain.doFilter(request, response);
    }
}
