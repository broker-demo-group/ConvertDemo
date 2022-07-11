package com.brokerdemo.brokerconvertdemoproject.configuration;

import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationEntryPoint;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationFailureHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationSuccessHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeLogoutSuccessHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    public SpringSecurityConfiguration(@Autowired UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(@Autowired AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder())
//                .withUser("zhou").password("123").roles("USER");
//        auth.authenticationProvider(myAuthenticationProvider);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    @Autowired
    CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        // 添加 jwt 认证
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .formLogin()
                .permitAll()
                // 认证成功时的处理器
                .successHandler(customizeAuthenticationSuccessHandler)
                // 认证失败时的处理器
                .failureHandler(customizeAuthenticationFailureHandler)
                .and()
                .cors()
                // 解决前后端分离的跨域问题
                .configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                .and()
                .csrf()
                .disable();

        //让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        // logout 成功后的处理器
        http.logout().logoutSuccessHandler(customizeLogoutSuccessHandler);
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
