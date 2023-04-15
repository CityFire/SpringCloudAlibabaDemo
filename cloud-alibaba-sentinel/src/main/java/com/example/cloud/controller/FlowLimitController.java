package com.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.sun.deploy.security.BlockedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
//@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    @SentinelResource("testA")
    public String testA() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "----------hello testA";
    }

    @GetMapping("/testB")
    @SentinelResource("testB")
    public String testB() {
        return "----------hello testB";
    }

    @GetMapping("/testC")
    @SentinelResource(value = "aaa", blockHandler = "testCBlockHandler", fallback = "testCFallback")
    public String testC(Integer id) {
        if (id < 0) {
            throw new RuntimeException("参数值格式错误");
        }
        return "--------testC";
    }

    // 自定义降级或熔断时的提示信息
    public String testCBlockHandler(Integer id, BlockException exception) {
        // 判断异常的类型
        if (exception instanceof FlowException) {
            return "你被限流了，请稍后再试";
        } else if (exception instanceof DegradeException) {
            return "你被降级了，请稍后再试";
        }
        return "当前服务不可用";
    }

    // 自定义未限流时发生异常的提示信息
    public String testCFallback(Integer id){
        return "参数不合法";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int age = 10/0;
        return "-------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        System.out.println("testE 测试异常数");
        int age = 10/0;
        return "-------testE 测试异常数";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
//        int age = 10/0;
        return "--------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockedException exception) {
        return "--------deal_testHotKey"; // sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }

}
