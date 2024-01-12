package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class OrderDetailDto {
    private UUID orderId;
    private LocalDateTime datePurchased;
    private double totalPrice;
    private List<OrderItemDetailDto> items;
}
