package com.brokerdemo.brokerconvertdemoproject.configuration;

import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationEntryPoint;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationFailureHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeAuthenticationSuccessHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.CustomizeLogoutSuccessHandler;
import com.brokerdemo.brokerconvertdemoproject.configuration.auth.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(@Autowired AuthenticationManagerBuilder auth) throws Exception {
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
    public void configure(WebSecurity web){
        //swagger2?????????????????????????????????????????????
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        // ?????? jwt ??????
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/asset/currencies").permitAll()
                .antMatchers("/asset/convert/currencies").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .formLogin()
                .permitAll()
                // ???????????????????????????
                .successHandler(customizeAuthenticationSuccessHandler)
                // ???????????????????????????
                .failureHandler(customizeAuthenticationFailureHandler)
                .and()
                .cors()
                // ????????????????????????????????????
                .configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                .and()
                .csrf()
                .disable();

//        ???Security??????????????????HttpSession??????????????????HttpSession?????????SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        // logout ?????????????????????
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

