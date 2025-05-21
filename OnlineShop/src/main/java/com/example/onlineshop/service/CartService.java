package com.example.onlineshop.service;

import com.example.onlineshop.entity.Cart;
import com.example.onlineshop.entity.CartItem;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.repository.CartRepository;
import com.example.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart getCart(String username) {
        return cartRepository.findByUsername(username)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUsername(username);
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public Cart addToCart(String username, Long productId, int quantity) {
        Cart cart = getCart(username);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProduct(product);
                    newItem.setCart(cart);
                    cart.getItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(String username, Long cartItemId) {
        Cart cart = getCart(username);

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));

        cart.getItems().remove(itemToRemove);
        return cartRepository.save(cart);
    }
}