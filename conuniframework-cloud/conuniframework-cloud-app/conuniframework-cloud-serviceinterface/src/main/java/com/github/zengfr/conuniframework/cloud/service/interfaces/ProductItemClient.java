package com.github.zengfr.conuniframework.cloud.service.interfaces;

import com.github.zengfr.conuniframework.cloud.service.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-b",
        url = "http://localhost:8181",
        path = "/product", configuration = FeignConfiguration.class)
public interface ProductItemClient {
    @GetMapping(value = "/")
    public String process();

    @GetMapping(value = "/test1")
    public String test1();

    @GetMapping(value = "/test2")
    public String test2();
}
