package com.example.nacos.controller;

import com.example.nacos.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    private final RestTemplate restTemplate;

    @Autowired
    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://cloud-nacos-provider-pay8001/echo/" + str, String.class);
    }

    @Autowired
    private UserClient userClient;

    @GetMapping("/consumer/get")
    public String get() {
        return userClient.get();
    }
}
