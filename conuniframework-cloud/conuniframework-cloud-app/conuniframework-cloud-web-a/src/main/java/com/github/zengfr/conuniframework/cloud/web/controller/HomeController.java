package com.github.zengfr.conuniframework.cloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** Created by zengfr on 2020/9/10. */
@Controller
public class HomeController {
  @GetMapping(value = "/")
  @ResponseBody
  public String index() {
    return "<a href=\"/test1\">test1</a>";
  }

  @GetMapping(value = "/test1")
  @ResponseBody
  public String test1() {
    return "test11 <a href=\"/test/2\">test2</a> <a href=\"/logout\">Logout</a>"+;
  }

  @GetMapping(value = "/test2")
  public String test2() {
    return "test22";
  }
}
