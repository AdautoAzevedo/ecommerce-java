package com.example.ecommerce_java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_java.dtos.AddToCartDTO;
import com.example.ecommerce_java.dtos.CartDTO;
import com.example.ecommerce_java.dtos.CartItemDTO;
import com.example.ecommerce_java.models.CartItem;
import com.example.ecommerce_java.services.CartItemService;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/cart/{cartId}")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, AddToCartDTO addToCartDTO) {
        CartDTO updatedCart = cartItemService.addToCart(cartId, addToCartDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);   
    }

    @PutMapping("/")
    public ResponseEntity<CartItemDTO> updateItem(@RequestBody CartItem cartItem) {
        CartItemDTO savedItem = cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<CartItemDTO>(savedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        cartItemService.removeFromCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
