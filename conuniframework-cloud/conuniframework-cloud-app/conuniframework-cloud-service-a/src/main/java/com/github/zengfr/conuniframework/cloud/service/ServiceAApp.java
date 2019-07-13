package com.github.zengfr.conuniframework.cloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableOAuth2Client

public class ServiceAApp {
  public static void main(String[] args) {

    SpringApplication.run(ServiceAApp.class, args);
  }
}
