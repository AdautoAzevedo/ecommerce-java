package com.example.ecommerce_java.dtos;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTO(Long id, UserDTO user, BigDecimal totalPrice, String status, List<OrderItemDTO> orderItems) {
}
