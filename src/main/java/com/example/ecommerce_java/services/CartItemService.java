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

    @Autowired CartService cartService;

    public CartItemDTO addToCart(Long cartId, CartItem cartItem) {
       
        Cart cart = cartService.getCartById(cartId);
        cartItem.setCart(cart);
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cartId, cartItem.getProduct().getId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            CartItem updatedItem = cartItemRepository.save(existingItem);
            cartService.updateCartTotalPrice(cart);
            return DTOMapper.toCartItemDTO(updatedItem);
        }

        CartItem savedItem = cartItemRepository.save(cartItem);
        cartService.updateCartTotalPrice(cart);
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
