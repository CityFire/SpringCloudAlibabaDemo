package com.example.nacos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cloud-nacos-provider-pay8001")
public interface UserClient {

    @GetMapping("/user/get")
    String get();
}
