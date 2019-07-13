package com.github.zengfr.conuniframework.cloud.web.controller;

import com.github.zengfr.conuniframework.cloud.service.interfaces.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Created by zengfr on 2020/9/17. */
@RestController
@RequestMapping(value = "/test")
public class TestController {
  @Autowired DiscoveryClient discoveryClient;
  @Autowired ProductClient productClient;

  @GetMapping(value = "/1")
  public String test1() {
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances("service-a");
    ServiceInstance serviceInstance = serviceInstances.get(0);
    return String.format(
        "%s,%s,%s,%s",
        serviceInstance.getServiceId(),
        serviceInstance.getHost(),
        serviceInstance.getPort(),
        "Hello");
  }

  @GetMapping(value = "/2")
  public String test2() {
    return productClient.test1();
  }

  @GetMapping(value = "/3")
  public String test3() {
    return productClient.test2();
  }
  @GetMapping(value = "/4")
  public String test4() {
    return productClient.test3();
  }

  @GetMapping(value = "/5")
  public String test5() {
    return productClient.test4();
  }
}
