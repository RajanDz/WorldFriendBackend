package com.example.worldFriend;


import com.example.worldFriend.model.Role;
import com.example.worldFriend.model.User;
import com.example.worldFriend.repository.RoleRepo;
import com.example.worldFriend.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = WorldFriendApplication.class)
public class UserTableTest {

    private static Logger logger = LoggerFactory.getLogger(UserTableTest.class);
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepository;

    @Test
    void createUser(){
        User user = new User("Maria","Nickle","maria@gmail.com","John2025", passwordEncoder.encode("MyPassword123"));
        userRepository.save(user);
        logger.info("User saved: {}", user);
    }

    @Test
    void createRole(){
        Role role = new Role("Admin");
        roleRepository.save(role);
        logger.info("Created role: {}", role);
    }

    @Test
    void assignRoleToUser(){
        User user = userRepository.findById(2).orElseThrow();
        Role role = roleRepository.findById(1).orElseThrow();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info("Role is assigned to user: {}", role,user);
    }
}
