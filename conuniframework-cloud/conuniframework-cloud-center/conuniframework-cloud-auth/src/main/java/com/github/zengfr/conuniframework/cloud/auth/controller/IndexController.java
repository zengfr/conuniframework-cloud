package com.github.zengfr.conuniframework.cloud.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/** Created by zengfr on 2020/9/10. */
@Controller
public class IndexController {
  @GetMapping(value = {"/", "index.html"})
  public String index() {
    return "redirect:signin";
  }

  @RequestMapping(value = {"/signin"})
  public String signin() {
    return "signin";
  }

  @RequestMapping(value = {"/signout"})
  public String signout() {
    return "signout";
  }

  @RequestMapping(value = {"/test"})
  @ResponseBody
  public String test() {
    return "test";
  }

  @RequestMapping("/msg")
  public ModelAndView msg(Model model) {
    model.addAttribute("type", true);
    model.addAttribute("msg", "登陆失败，账号或者密码错误！");
    return new ModelAndView("login", "userModel", model);
  }
}
