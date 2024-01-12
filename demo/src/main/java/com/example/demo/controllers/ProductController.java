package com.example.demo.controllers;

import com.example.demo.dto.CreateProductDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.findAll();
    }


    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto productDto) throws IOException {

        return ResponseEntity.ok(productService.save(productDto));

    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {

        Product product = productService.findById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable UUID productId,
                                           @ModelAttribute ProductUpdateDto productUpdateDto) {
        try {
            Product updatedProduct = productService.updateProduct(productId, productUpdateDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}/availability")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateProductAvailability(@PathVariable UUID productId,
                                                       @RequestParam("isAvailable") boolean isAvailable) {
        try {
            Product updatedProduct = productService.updateProductAvailability(productId, isAvailable);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
