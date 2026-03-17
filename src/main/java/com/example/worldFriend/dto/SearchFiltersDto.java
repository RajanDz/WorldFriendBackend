package com.example.worldFriend.dto;


import com.example.worldFriend.enums.LocationType;
import lombok.Getter;

@Getter
public class SearchFiltersDto {
    private String name;
    private String type;
    private String country;
    private String city;
}
