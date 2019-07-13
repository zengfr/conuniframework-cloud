package com.github.zengfr.conuniframework.cloud.service;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringCloudApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableOAuth2Client
public class ServiceBApp {
  public static void main(String[] args) {

    SpringApplication.run(ServiceBApp.class, args);
  }
}
