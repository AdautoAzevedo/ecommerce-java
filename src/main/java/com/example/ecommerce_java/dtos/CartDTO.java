package com.example.ecommerce_java.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CartDTO(Long id, UserDTO user, String status, BigDecimal totalPrice, List<CartItemDTO> cartItems) {
    
}
