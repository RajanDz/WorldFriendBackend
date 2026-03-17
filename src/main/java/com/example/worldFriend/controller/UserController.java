package com.example.worldFriend.controller;

import com.example.worldFriend.generics.ApiResponse;
import com.example.worldFriend.model.User;
import com.example.worldFriend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/getUserProfile")
    public ResponseEntity<ApiResponse<User>> me(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Authentication is null!");
        }
        User authUser = userService.findUserByUsername(user.getUsername());
         return ResponseEntity.ok(ApiResponse.success("Auth user details", authUser));
    }
}
