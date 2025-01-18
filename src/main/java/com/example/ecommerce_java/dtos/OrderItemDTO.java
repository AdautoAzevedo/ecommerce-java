package com.example.ecommerce_java.dtos;

import java.math.BigDecimal;

public record OrderItemDTO(Long id, ProductDTO product, Integer quantity, BigDecimal price) {
}
