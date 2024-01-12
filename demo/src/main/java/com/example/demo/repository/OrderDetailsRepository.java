package com.example.demo.repository;

import com.example.demo.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {
}
