package com.example.demo.controllers;

import com.example.demo.dto.OrderDetailDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDto orderDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentEmailAdress = authentication.getName();
        User user = userRepository.findByEmail(currentEmailAdress)
                .orElseThrow(() -> new RuntimeException("User not found."));


        Order order = orderService.createOrder(orderDto, user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("my")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        List<Order> orders = orderService.getOrdersByUserEmail(userEmail);

        return ResponseEntity.ok(orders);
    }



    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDetailDto> getOrderDetails(@PathVariable UUID orderId, Authentication authentication) {
        String userEmail = authentication.getName();

        OrderDetailDto orderDetail = orderService.getOrderDetailsForUser(orderId, userEmail);
        return ResponseEntity.ok(orderDetail);
    }




}
