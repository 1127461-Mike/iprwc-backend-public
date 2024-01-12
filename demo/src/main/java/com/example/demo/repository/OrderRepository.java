package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> getOrdersByUserEmail(String userEmail);

    List<Order> findByUserId(UUID userId);

    Optional<Order> findByIdAndUserEmail(UUID orderId, String userEmail);
}
