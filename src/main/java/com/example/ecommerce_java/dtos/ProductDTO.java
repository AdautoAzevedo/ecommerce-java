package com.example.ecommerce_java.dtos;

import java.math.BigDecimal;

public record ProductDTO(Long productId, String name, BigDecimal price, SimplifiedCategoryDTO category) {
}
