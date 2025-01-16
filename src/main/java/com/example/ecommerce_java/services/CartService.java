package com.example.ecommerce_java.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

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

    public CartDTO createOrGetCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart activeCart = cartRepository.findByUserIdAndStatus(userId, "active").get();

        if (activeCart != null) {
            return DTOMapper.toCartDTO(activeCart);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus("active");
        cart.setCartItems(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        Cart savedCart = cartRepository.save(cart);
        return DTOMapper.toCartDTO(savedCart);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void findActiveCartByUserId(Long userId) {
        Cart activeCart = cartRepository.findByUserIdAndStatus(userId, "active").orElse(null);
        if (activeCart == null) {
            createOrGetCartForUser(userId);
        }
    }

    public CartDTO getCartDTOById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return DTOMapper.toCartDTO(cart);
    }

    public CartDTO getCartDTOByUser(Long userId) {
        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return DTOMapper.toCartDTO(cart);
    }
    
    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart;
    }
    
    public void updateCartTotalPrice(Cart cart) {
        BigDecimal updatedTotalPrice = cart.getCartItems().stream()
            .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(updatedTotalPrice);
        cartRepository.save(cart);
    }

    public CartDTO updateCart(Cart cart) {
        Cart updatedCart = cartRepository.save(cart);
        return DTOMapper.toCartDTO(updatedCart);
    }

    public void deleteCartById(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
