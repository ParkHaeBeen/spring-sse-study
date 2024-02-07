package com.example.springssestudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  public String user() {
    return "user";
  }

}
