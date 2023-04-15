package com.example.cloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {

    @Override
    public String paymentSQL(Long id) {
        return "4444-服务降级返回,-----PaymentFallbackService";
    }
}
