package com.example.ecommerce_java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.OrderDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.Order;
import com.example.ecommerce_java.models.OrderItem;
import com.example.ecommerce_java.models.User;
import com.example.ecommerce_java.repositories.CartRepository;
import com.example.ecommerce_java.repositories.OrderRepository;
import com.example.ecommerce_java.repositories.UserRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public OrderDTO createOrder(Long userId) {
        User user = findUser(userId);
        Cart cart = cartRepository.findByUserIdAndStatus(userId, "active").get();
        

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus("unpaid");

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cart.setStatus("inactive");
        cartRepository.save(cart);
        return DTOMapper.toOrderDTO(savedOrder);
    }

    public List<OrderDTO> getOrderByUser(Long userId) {
        User user = findUser(userId);
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(DTOMapper::toOrderDTO).collect(Collectors.toList());
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
