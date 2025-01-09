package com.example.ecommerce_java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.CartItemDTO;
import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.CartItem;
import com.example.ecommerce_java.repositories.CartItemRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItemDTO addToCart(Cart cart, CartItem cartItem) {
        cartItem.setCart(cart);
        CartItem savedItem =  cartItemRepository.save(cartItem);
        return DTOMapper.toCartItemDTO(savedItem);
    }
    
    public CartItemDTO updateCartItem(CartItem cartItem) {
        CartItem updatedItem = cartItemRepository.save(cartItem);
        return DTOMapper.toCartItemDTO(updatedItem);
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
