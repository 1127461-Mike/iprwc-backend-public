package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class OrderItemDto {
    private UUID productID;
    private int quantity;

    public UUID getProductId() {
        return this.productID;
    }
}
