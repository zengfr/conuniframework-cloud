package com.github.zengfr.conuniframework.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

// @SpringBootApplication
// @EnableWebMvc
@SpringCloudApplication
@EnableDiscoveryClient
@ComponentScan(
    value = {
      "com.github.zengfr.conuniframework.cloud.auth",
      "com.github.zengfr.conuniframework.cloud.auth.config",
      "com.github.zengfr.conuniframework.cloud.auth.controller"
    })
@EnableResourceServer
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
