package com.github.zengfr.conuniframework.cloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RefreshScope
@RestController
@RequestMapping(value = "/product")
public class ProductController {
  @Autowired
  DiscoveryClient discoveryClient;

  @GetMapping(value = "/")
  public String process() {
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances("service-b");
    ServiceInstance serviceInstance = serviceInstances.get(0);
    return String.format(
        "%s,%s,%s,%s",
        serviceInstance.getServiceId(),
        serviceInstance.getHost(),
        serviceInstance.getPort(),
        "Hello");
  }

  @GetMapping(value = "/processA")
  public String processA() {
    return "processA";
  }

  @GetMapping(value = "/processB")
  public String processB() {
    return "processB";
  }
}
