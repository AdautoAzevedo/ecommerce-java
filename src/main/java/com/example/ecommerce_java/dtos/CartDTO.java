package com.example.ecommerce_java.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.ecommerce_java.models.CartItem;

public record CartDTO(Long id, UserDTO user, String status, BigDecimal totalPrice, List<CartItem> cartItems) {
    
}
