package com.example.worldFriend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthResponse {
    private final String jwt;
    private String type = "Bearer";
}
