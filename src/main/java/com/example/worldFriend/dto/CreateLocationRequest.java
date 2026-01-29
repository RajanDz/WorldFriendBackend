package com.example.worldFriend.dto;

import com.example.worldFriend.enums.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateLocationRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocationType type;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
}
