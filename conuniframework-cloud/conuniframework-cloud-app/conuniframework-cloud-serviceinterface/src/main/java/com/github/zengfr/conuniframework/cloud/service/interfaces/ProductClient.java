package com.github.zengfr.conuniframework.cloud.service.interfaces;

import com.github.zengfr.conuniframework.cloud.service.config.FeignConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(value = "service-a",
        url = "http://localhost:8180",
        path = "/product", configuration = FeignConfiguration.class)
public interface ProductClient {
    @GetMapping(value = "/")
    public String process();

    @GetMapping(value = "/test1")
    public String test1();

    @GetMapping(value = "/test2")
    public String test2();
    @GetMapping(value = "/test3")
    public String test3();

    @GetMapping(value = "/test4")
    public String test4();

}
