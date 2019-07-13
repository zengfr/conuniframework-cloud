package com.github.zengfr.conuniframework.cloud.service.controller;

import com.github.zengfr.conuniframework.cloud.service.interfaces.ProductItemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RefreshScope // @EnableOAuth2Resource
@RestController
@RequestMapping(value = "/product")
public class ProductController {
  @Autowired DiscoveryClient discoveryClient;
  @Autowired
  ProductItemClient productItemClient;
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

  @GetMapping(value = "/test1")
  public String test1() {
    return new Date() + "test1";
  }

  @GetMapping(value = "/test2")
  public String test2() {
    return new Date() + "test2";
  }

  @GetMapping(value = "/test3")
  public String test3() {
    return productItemClient.test1() + "test3";
  }

  @GetMapping(value = "/test4")
  public String test4() {
    return productItemClient.test2() + "test4";
  }
}
