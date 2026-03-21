package com.example.worldFriend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchReturnListDto {


    private final Long id;
    private final String name;
    private final String country;
}
