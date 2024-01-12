package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    
    private String email;
    private String name;
    private String surname;
    private String password;
    private String postalCode;
    private String address;
    private String phoneNumber;
}
