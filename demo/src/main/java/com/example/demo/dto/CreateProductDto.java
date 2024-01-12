package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDto {
    private String name;
    private String description;
    private double price;
    private String brandName;
    private String imagePath;
}
