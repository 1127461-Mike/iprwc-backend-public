package com.example.demo.controllers;

import com.example.demo.dto.OrderDetailDto;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<OrderDetailDto> getOrderDetails(@PathVariable UUID orderId) {
        OrderDetailDto orderDetail = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/users/{userEmail}/orders")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userEmail) {
        List<Order> orders = orderService.getOrdersByUserEmail(userEmail);
        return ResponseEntity.ok(orders);
    }
}
