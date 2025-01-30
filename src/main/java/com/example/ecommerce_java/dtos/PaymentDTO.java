package com.example.ecommerce_java.dtos;

public record PaymentDTO(Long orderId, String currency, String receiptEmail) {
}
