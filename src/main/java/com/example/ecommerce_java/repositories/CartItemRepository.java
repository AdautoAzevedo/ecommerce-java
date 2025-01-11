package com.example.ecommerce_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
