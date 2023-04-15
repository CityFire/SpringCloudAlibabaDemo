package com.example.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NanosProviderPay8001 {
    public static void main(String[] args) {
        SpringApplication.run(NanosProviderPay8001.class, args);
    }
}
