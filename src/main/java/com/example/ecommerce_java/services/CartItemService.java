package com.example.ecommerce_java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.AddToCartDTO;
import com.example.ecommerce_java.dtos.CartDTO;
import com.example.ecommerce_java.dtos.CartItemDTO;
import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.CartItem;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.CartItemRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired 
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public CartDTO addToCart(Long cartId, AddToCartDTO addToCartDTO) {
       
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(addToCartDTO.productId());

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(addToCartDTO.productId()))
                .findFirst()
                .orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + addToCartDTO.quantity());
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(addToCartDTO.quantity());
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }

        cartService.updateCartTotalPrice(cart);
        Cart updatedCart = cartService.saveCart(cart);
        return DTOMapper.toCartDTO(updatedCart);
    }
    
    public CartItemDTO updateCartItem(CartItem cartItem) {
        CartItem updatedItem = cartItemRepository.save(cartItem);
        return DTOMapper.toCartItemDTO(updatedItem);
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
