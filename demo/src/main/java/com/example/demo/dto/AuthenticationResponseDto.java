package com.example.demo.dto;

public class AuthenticationResponseDto {
    private final String jwt;

    public AuthenticationResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}