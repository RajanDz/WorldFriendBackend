package com.example.worldFriend.service;

import com.example.worldFriend.model.User;
import com.example.worldFriend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepository;

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }
}
