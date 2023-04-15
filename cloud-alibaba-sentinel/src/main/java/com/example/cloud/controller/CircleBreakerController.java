package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.cloud.service.PaymentService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://cloud-nacos-provider-pay8001";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") //没有配置
//    @SentinelResource(value = "fallback", fallback = "handlerFallback") // fallback只负责业务异常
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler") // blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler",
           exceptionsToIgnore = {IllegalArgumentException.class}) // 忽略属性
    public String fallback(@PathVariable Long id) {
        String result = restTemplate.getForObject(SERVICE_URL + "/echo/" + id, String.class);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常.....");
        } else if (id > 9) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }
        return result;
    }

    // fallback
    public String handlerFallback(@PathVariable Long id, Throwable e) {
        return "444兜底异常handlerFallback,exception内容" + e.getMessage();
    }

    // blockHandler
    public String blockHandler(@PathVariable Long id, BlockException blockException) {
        return "445 blockHandler-sentinel限流，无此流水：,blockException" + blockException.getMessage();
    }

    //===================OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public String paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
