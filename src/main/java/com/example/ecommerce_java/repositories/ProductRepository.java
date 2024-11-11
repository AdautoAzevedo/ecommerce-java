package com.example.ecommerce_java.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategory(Long categoryId);
}
