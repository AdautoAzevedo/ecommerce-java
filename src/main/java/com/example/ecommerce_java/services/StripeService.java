package com.example.ecommerce_java.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.PaymentDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
import com.example.ecommerce_java.models.Order;
import com.example.ecommerce_java.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class StripeService {
    private final OrderRepository orderRepository;

    public StripeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String createPaymentIntent(PaymentDTO paymentDTO) throws StripeException {
        Order order = orderRepository.findById(paymentDTO.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        //Create payment intent parameters
        Map<String, Object> params = new HashMap<>();
        params.put("amount", order.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValue()); //Convert to cents
        params.put("currency", paymentDTO.currency());
        params.put("receipt_email", paymentDTO.receiptEmail());
        params.put("description", "Payment for order ID: " + order.getId());

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent.getClientSecret();
    }
}
