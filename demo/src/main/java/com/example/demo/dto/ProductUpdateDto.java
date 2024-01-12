package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductUpdateDto {
    private String name;
    private String description;
    private Double price;
    private String brandName;
    private MultipartFile image;

}
