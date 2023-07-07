package com.huoxiaolu.springsecurity.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

  @RequestMapping("/hello")
  public String hello(){
    return "欢迎来到登陆页面";
  }

  @RequestMapping("/")
  @ResponseBody
  public String index(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return JSON.toJSONString(authentication);
  }
  @RequestMapping("/authentication")
  @ResponseBody
  public String index(Authentication authentication){
    return JSON.toJSONString(authentication);
  }
  @RequestMapping("/async")
  @ResponseBody
  public String async(){
    new Thread(new Runnable() {
      @Override
      public void run() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(JSON.toJSON(authentication));
      }
    }).start();
    return JSON.toJSONString("success");
  }

  @RequestMapping("/loginFail")
  public String loginFail(){
    return "login";
  }
}
