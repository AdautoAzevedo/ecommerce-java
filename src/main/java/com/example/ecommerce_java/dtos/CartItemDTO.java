package com.example.ecommerce_java.dtos;

public record CartItemDTO(Long id, Long cartId, ProductDTO product, Integer quantity) {
    
}
