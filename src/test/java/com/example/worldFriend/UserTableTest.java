package com.example.worldFriend;


import com.example.worldFriend.enums.LocationType;
import com.example.worldFriend.model.City;
import com.example.worldFriend.model.Location;
import com.example.worldFriend.model.Role;
import com.example.worldFriend.model.User;
import com.example.worldFriend.repository.CitiesRepository;
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

    @Autowired
    private CitiesRepository citiesRepository;

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
        City city = citiesRepository.findById(1L).orElseThrow();
        Location location = new Location("City kvart","City kvart je lijep i miran kvart u kojem sve ima.", LocationType.ATTRACTION,"Crna Gora", "Podgorica", 0.0,0.0,city);
        locationRepository.save(location);
        logger.info("Saved object: {}", location);
    }


    @Test
    void insertCity(){
        City city = new City("Milan", "Milano je jedan od najposjecenih gradova u Italiji i cijeloj evropi",0.0,0.0, "Italy");
        citiesRepository.save(city);
        logger.info("Saved object: {}", city);
    }
}
