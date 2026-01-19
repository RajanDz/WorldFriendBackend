package com.example.worldFriend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegistrationRequest {


    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
