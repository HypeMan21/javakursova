package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUsername(String username);
}