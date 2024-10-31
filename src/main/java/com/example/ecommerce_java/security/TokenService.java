package com.example.ecommerce_java.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.models.UserPrincipal;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserPrincipal userPrincipal) {
        
    }
}
