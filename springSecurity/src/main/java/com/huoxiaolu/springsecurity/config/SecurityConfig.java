package com.huoxiaolu.springsecurity.config;


import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

  /**
   * authorizeHttpRequests（）：开启权限配置
   * anyRequest().authenticated() 所有请求都需要认证
   * and()返回HttpSecurity，从头开始配置
   * formLogin()开启表单登陆，引入表单登陆过滤器(默认也会引入)
   * loginPage("/login.html") 自定义登录页面
   * loginProcessingUrl("/login") 定义登录提交的接口地址
   * defaultSuccessUrl 设置登录成功后的跳转地址
   * failureUrl 设置登录失败后的跳转地址
   * permitAll() 允许这些地址不需要认证
   * csrf().disable()security 支持csrf 防护，默认是开启，为了测试方便，这里暂时禁用
   */
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests((auth) ->{
//      try {
//        auth.anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login.html")
//            .loginProcessingUrl("/login")
//            .defaultSuccessUrl("/")
//            .failureUrl("/login.html")
//            .permitAll()
//            .and()
//            .csrf().disable();
//      } catch (Exception e) {
//        throw new RuntimeException(e);
//      }
//
//    });
//    return http.build();
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((auth) ->{
      try {
        auth.anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/")
            .failureForwardUrl("/loginFail")
            .usernameParameter("yonghuming") // 自定义用户名密码的参数，需要与前端页面static包下的login.html中修改参数，两者相匹配才可以登陆成功
            .passwordParameter("mima")
//                        .successHandler(new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                Map<String,Object> result = new HashMap<>();
//                                result.put("code",0);
//                                result.put("msg","登陆成功");
//                                result.put("data",authentication);
//                                writeResp(result,response);
//                            }
//                        })
//                        .failureHandler(new AuthenticationFailureHandler() {
//                            @Override
//                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                                Map<String,Object> result = new HashMap<>();
//                                result.put("code",-1);
//                                result.put("msg","登陆失败");
//                                result.put("data",exception.getMessage());
//                                writeResp(result,response);
//                            }
//                        })
            .permitAll()
            .and()
            .csrf().disable();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    });
    return http.build();
  }
  public void writeResp(Object content, HttpServletResponse response) {
    response.setContentType("application/json;charset=utf-8");
    try {
      response.getWriter().println(JSON.toJSONString(content));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
