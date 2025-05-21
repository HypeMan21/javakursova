package com.example.onlineshop.controller;

import com.example.onlineshop.entity.Order;
import com.example.onlineshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(Authentication authentication) {
        String username = authentication.getName();
        Order order = orderService.createOrderFromCart(username);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getOrders(Authentication authentication) {
        String username = authentication.getName();
        Iterable<Order> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }
}