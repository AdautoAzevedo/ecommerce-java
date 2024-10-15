package com.example.ecommerce_java.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_java.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLogin(String login);
}
