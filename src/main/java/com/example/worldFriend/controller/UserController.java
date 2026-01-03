package com.example.worldFriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public String helloWorld(){
        return "hello world!";
    }
}
