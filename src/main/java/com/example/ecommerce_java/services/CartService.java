package com.example.ecommerce_java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.CartDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
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

    public CartDTO createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus("active");
        Cart savedCart = cartRepository.save(cart);
        return DTOMapper.toCartDTO(savedCart);
    }

    public CartDTO getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return DTOMapper.toCartDTO(cart);
    }

    public CartDTO updateCart(Cart cart) {
        Cart updatedCart = cartRepository.save(cart);
        return DTOMapper.toCartDTO(updatedCart);
    }

    public void deleteCartById(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
