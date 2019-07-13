package com.github.zengfr.conuniframework.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableDiscoveryClient
@EnableConfigServer
// @SpringBootApplication
@SpringBootApplication(
    exclude = {
      SecurityAutoConfiguration.class,
      org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
      org.springframework.boot.actuate.autoconfigure.security.servlet
          .ManagementWebSecurityAutoConfiguration.class
    })
public class ConfigApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConfigApplication.class, args);
  }
}
