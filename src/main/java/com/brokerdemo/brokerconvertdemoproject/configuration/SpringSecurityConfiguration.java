package com.brokerdemo.brokerconvertdemoproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Collections;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义登录身份认证组件
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();
        // 退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }



//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(@Autowired AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder());
//    }
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Autowired
//    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//    @Autowired
//    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;
//
//    @Autowired
//    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
//
//    @Autowired
//    CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;
//
//    @Autowired
//    CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
//
//    @Override
//    public void configure(WebSecurity web){
//        //swagger2所需要用到的静态资源，允许访问
//        web.ignoring().antMatchers("/v2/api-docs",
//                "/swagger-resources/configuration/ui",
//                "/swagger-resources",
//                "/swagger-resources/configuration/security",
//                "/swagger-ui.html");
//    }
//
//    @SneakyThrows
//    @Override
//    protected void configure(HttpSecurity http) {
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                // 跨域预检请求
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                // 登录URL
//                .antMatchers("/login").permitAll()
//                // swagger
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/swagger-resources").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
//                // 其他所有请求需要身份认证
//                .anyRequest().authenticated();
//        // 退出登录处理器
//        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
//        // 开启登录认证流程过滤器
//        http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//        // 访问控制时登录状态检查过滤器
//        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
////
//////        // 添加 jwt 认证
////        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
////
////        http.authorizeRequests()
////                .antMatchers("/register/**").permitAll()
////                .antMatchers("/swagger-ui/**").permitAll()
////                .antMatchers("/asset/currencies").permitAll()
////                .antMatchers("/asset/convert/currencies").permitAll()
////                .antMatchers("/api/**").permitAll()
////                .antMatchers("/v2/**").permitAll()
////                .antMatchers("/login").permitAll()
////                .antMatchers("/logout").permitAll()
////                .antMatchers("/restlogin").permitAll()
////                .anyRequest().authenticated()
////                .and().httpBasic()
////                .and()
////                .formLogin()
////                .permitAll()
////                // 认证成功时的处理器
////                .successHandler(customizeAuthenticationSuccessHandler)
////                // 认证失败时的处理器
//////                .failureHandler(customizeAuthenticationFailureHandler)
////                .and()
////                .cors()
////                // 解决前后端分离的跨域问题
////                .configurationSource(corsConfigurationSource())
////                .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
////                .and()
////                .csrf()
////                .disable();
////
//////        让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
////        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and().headers().cacheControl();
////
////        // logout 成功后的处理器
////        http.logout().logoutSuccessHandler(customizeLogoutSuccessHandler);
//        http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//    }
//
//
//
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}

