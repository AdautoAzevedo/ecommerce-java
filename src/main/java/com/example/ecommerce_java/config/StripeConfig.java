package com.example.ecommerce_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class StripeConfig {
    @Bean
    public String stripeSecretKey() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("STRIPE_SECRET_KEY");
    }
}
