package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findByUsername(String username);
}