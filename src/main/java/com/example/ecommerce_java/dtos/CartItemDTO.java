package com.example.ecommerce_java.dtos;

public record CartItemDTO(Long id, CartDTO cart, ProductDTO product, Integer quantity) {
    
}
