package com.example.ecommerce_java.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserIdAndStatus(Long userId, String status);
}
