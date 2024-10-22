package com.example.ecommerce_java.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.models.Order;
import com.example.ecommerce_java.models.User;
import com.example.ecommerce_java.repositories.OrderRepository;
import com.example.ecommerce_java.repositories.UserRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Order createOrder(Long userId, BigDecimal totalPrice) {
        User user = findUser(userId);
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus("active");
        return orderRepository.save(order);
    }

    public List<Order> getOrderByUser(Long userId) {
        User user = findUser(userId);
        return orderRepository.findByUser(user);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
