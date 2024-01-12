package com.example.demo.controllers;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping()
    public List<Cart> getAllCartItems(){
        return cartService.findAll();
    }
}
