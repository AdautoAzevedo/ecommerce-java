package com.example.ecommerce_java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_java.dtos.PaymentDTO;
import com.example.ecommerce_java.services.StripeService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentDTO paymentDTO) {
        try {
            String clientSecret = stripeService.createPaymentIntent(paymentDTO);
            return new ResponseEntity<String>(clientSecret, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>("Payment processing failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
