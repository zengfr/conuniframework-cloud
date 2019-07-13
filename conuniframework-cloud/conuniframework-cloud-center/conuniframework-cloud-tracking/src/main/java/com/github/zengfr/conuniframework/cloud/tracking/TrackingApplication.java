package com.github.zengfr.conuniframework.cloud.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.server.internal.EnableZipkinServer;

/**
 * @author
 */
@SpringBootApplication
@EnableZipkinServer
@Configuration
public class TrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingApplication.class, args);
    }

  @Bean
  public UndertowServletWebServerFactory setUndertow() {
    UndertowServletWebServerFactory undertow = new UndertowServletWebServerFactory();
    undertow.setContextPath("/"); // 设置项目访问路径
    undertow.setPort(9411); // 设置项目端口号
    return undertow;
        }
}
