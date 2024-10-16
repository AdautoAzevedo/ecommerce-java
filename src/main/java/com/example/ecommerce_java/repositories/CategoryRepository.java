package com.example.ecommerce_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
