package com.example.ecommerce_java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.User;
import com.example.ecommerce_java.repositories.CartRepository;
import com.example.ecommerce_java.repositories.UserRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus("active");
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCartById(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
