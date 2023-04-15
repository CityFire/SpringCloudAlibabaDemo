package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.cloud.myhandler.CustomerBlockHandler;
import com.sun.deploy.security.BlockedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public String byResource() {
        return "按资源名称限流测试OK";
    }

    public String handleException(BlockedException exception) {
        return "444服务不可用";
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public String byUrl() {
        return "按url限流测试OK";
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException2")
    public String customerBlockHandler() { // @SentinelResource和@HystrixCommand的作用是类似的，都是来指定流控或降级时返回的信息
        return "200按客户自定义";
    }
}
