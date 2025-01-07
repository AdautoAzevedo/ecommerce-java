package com.example.ecommerce_java.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.ecommerce_java.models.UserPrincipal;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserPrincipal userPrincipal) {
        try{
            System.out.println(userPrincipal);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("ecommerce-api")
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
            System.out.println(token);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while genenrating token: ", exception);
        }
    }

    public String validateToken(String token) {
        try {
            
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
           System.out.println("JWT verification exception: " + e);
           return "JWT verification exception: " + e;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
