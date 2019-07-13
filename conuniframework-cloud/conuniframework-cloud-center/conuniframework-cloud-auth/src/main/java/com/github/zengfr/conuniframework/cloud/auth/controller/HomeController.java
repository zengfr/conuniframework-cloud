package com.github.zengfr.conuniframework.cloud.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/home"})
public class HomeController {
  @GetMapping(value = {"/", "/index", "/index.html"})
  public String index() {
    return "redirect:/home/home";
  }
  @GetMapping(value = {"home"})
  public String home() {
    return "home";
  }


}
