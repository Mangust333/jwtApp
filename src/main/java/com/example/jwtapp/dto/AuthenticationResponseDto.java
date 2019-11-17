package com.example.jwtapp.dto;

import lombok.Value;

@Value
public class AuthenticationResponseDto {

    private String username;

    private String token;
}
