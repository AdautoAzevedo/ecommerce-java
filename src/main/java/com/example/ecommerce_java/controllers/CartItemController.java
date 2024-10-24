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

import com.example.ecommerce_java.models.Cart;
import com.example.ecommerce_java.models.CartItem;
import com.example.ecommerce_java.services.CartItemService;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody Cart cart, CartItem cartItem) {
        CartItem savedItem = cartItemService.addToCart(cart, cartItem);
        return new ResponseEntity<CartItem>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CartItem> updateItem(@RequestBody CartItem cartItem) {
        CartItem savedItem = cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<CartItem>(savedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        cartItemService.removeFromCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
