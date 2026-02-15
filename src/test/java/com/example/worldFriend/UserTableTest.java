package com.example.worldFriend;


import com.example.worldFriend.enums.LocationType;
import com.example.worldFriend.model.Location;
import com.example.worldFriend.model.Role;
import com.example.worldFriend.model.User;
import com.example.worldFriend.repository.LocationRepository;
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

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void createUser(){
        User user = new User("Sucko","Dzaferadzovic","sukijano","suki@gmail.com", passwordEncoder.encode("MyPassword123"));
        userRepository.save(user);
        logger.info("User saved: {}", user);
    }

    @Test
    void createRole(){
        Role role = new Role("LOC_ADMIN");
        roleRepository.save(role);
        logger.info("Created role: {}", role);
    }

    @Test
    void assignRoleToUser(){
        User user = userRepository.findByUsername("arronDenmark2026").orElseThrow();
        Role role = roleRepository.findById(2).orElseThrow();
        Set<Role> roles = user.getRoles();
        roles.add(role);
        userRepository.save(user);
        logger.info("Role is assigned to user: {}", role,user);
    }

    @Test
    void createLocation(){
        Location location = new Location("Fontanta","Fontana je atrakcija dugi niz godina glavnog grada Podgorica. Okuplja kako mladje tako i starije koji vezu svoje uspomene za taj dio grada.", LocationType.ATTRACTION,"Crna Gora", "Podgorica", 0.0,0.0);
       locationRepository.save(location);
       logger.info("Saved object: {}", location);
    }
}
