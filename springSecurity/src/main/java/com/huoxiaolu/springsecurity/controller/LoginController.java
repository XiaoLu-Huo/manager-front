package com.huoxiaolu.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @RequestMapping("/hello")
  public String hello(){
    return "欢迎来到登陆页面";
  }
}
