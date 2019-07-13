package com.github.zengfr.conuniframework.cloud.web;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.github.zengfr.conuniframework.cloud.service")
@EnableCircuitBreaker
@EnableOAuth2Client
@ComponentScan("com.github.zengfr.conuniframework.cloud.web")
@ComponentScan("com.github.zengfr.conuniframework.cloud.service")
public class WebApp {
  public static void main(String[] args) {
    SpringApplication.run(WebApp.class, args);
  }
}
