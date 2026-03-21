package com.example.worldFriend.dto;

import lombok.Getter;

@Getter
public class SearchReturnListDto {
    public SearchReturnListDto(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    private Long id;
    private String name;
    private String country;
}
