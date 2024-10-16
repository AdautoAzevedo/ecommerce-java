package com.example.ecommerce_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
