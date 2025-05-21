package com.example.onlineshop.controller;

import com.example.onlineshop.entity.Cart;
import com.example.onlineshop.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        String username = authentication.getName();
        Cart cart = cartService.getCart(username);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestBody AddToCartRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        Cart cart = cartService.addToCart(username, request.productId(), request.quantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable Long cartItemId,
            Authentication authentication) {
        String username = authentication.getName();
        Cart cart = cartService.removeFromCart(username, cartItemId);
        return ResponseEntity.ok(cart);
    }
}

record AddToCartRequest(Long productId, int quantity) {}