package com.wind.blog.config;

import com.alibaba.fastjson.JSONObject;
import com.wind.blog.entity.RestBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/13  9:45
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
      return http
              .authorizeHttpRequests()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginProcessingUrl("/auth/login")
              .successHandler(this::onAuthenticationSuccess)
              .failureHandler(this::onAuthenticationFailure)
              .and()
              .logout()
              .logoutUrl("/auth/logout")
              .and()
              .csrf()
              .disable()
              .exceptionHandling()
              .authenticationEntryPoint(this::onAuthenticationFailure)
              .and()
              .build();

    }

   

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.failure(401,exception.getMessage())));
    }
    

}
