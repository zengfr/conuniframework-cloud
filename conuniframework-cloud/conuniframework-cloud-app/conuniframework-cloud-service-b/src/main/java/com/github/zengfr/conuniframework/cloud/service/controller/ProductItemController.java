package com.github.zengfr.conuniframework.cloud.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RefreshScope
@RestController
@RequestMapping(value = "/productitem")
public class ProductItemController {
  @Autowired DiscoveryClient discoveryClient;

  @GetMapping(value = "/test1")
  public String test1() {
    return new Date() + "test1";
  }

  @GetMapping(value = "/test2")
  public String test2() {
    return new Date() + "test2";
  }
}
