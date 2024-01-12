package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemDetailDto {
    private UUID productId;
    private String productName;
    private double productPrice;
    private int quantity;
}
