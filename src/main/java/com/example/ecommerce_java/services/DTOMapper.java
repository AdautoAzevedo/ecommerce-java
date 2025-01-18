package com.example.ecommerce_java.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce_java.dtos.CartDTO;
import com.example.ecommerce_java.dtos.CartItemDTO;
import com.example.ecommerce_java.dtos.OrderDTO;
import com.example.ecommerce_java.dtos.OrderItemDTO;
import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.dtos.SimplifiedCategoryDTO;
import com.example.ecommerce_java.dtos.UserDTO;
import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.CartItem;
import com.example.ecommerce_java.models.Order;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.models.User;

public class DTOMapper {
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getName(), user.getLogin());
    }

    public static ProductDTO toProductDTO(Product product) {
        SimplifiedCategoryDTO categoryDTO = new SimplifiedCategoryDTO(product.getCategory().getId(), product.getCategory().getCategoryName());
        return new ProductDTO(
            product.getId(), 
            product.getName(), 
            product.getPrice(), 
            categoryDTO);
    }

    public static CartItemDTO toCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(
            cartItem.getId(), 
            cartItem.getCart().getId(), 
            toProductDTO(cartItem.getProduct()),
            cartItem.getQuantity());
    }

    public static CartDTO toCartDTO(Cart cart) {
        return new CartDTO(
            cart.getId(),
            toUserDTO(cart.getUser()), 
            cart.getStatus(), 
            cart.getTotalPrice(), 
             cart.getCartItems() != null
            ? cart.getCartItems().stream().map(DTOMapper::toCartItemDTO).collect(Collectors.toList())
            : new ArrayList<>());
    }

    public static OrderDTO toOrderDTO(Order order) {
        List<OrderItemDTO> orderItems = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                    item.getId(),
                    toProductDTO(item.getProduct()),
                    item.getQuantity(),
                    item.getPrice()))
                .collect(Collectors.toList());
        return new OrderDTO(
            order.getId(),
            toUserDTO(order.getUser()), 
            order.getTotalPrice(), 
            order.getStatus(), 
            orderItems);
    }
}
