package com.example.worldFriend.service;


import com.example.worldFriend.dto.RegistrationRequest;
import com.example.worldFriend.generics.ApiResponse;
import com.example.worldFriend.model.Role;
import com.example.worldFriend.model.User;
import com.example.worldFriend.repository.RoleRepo;
import com.example.worldFriend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepo userRepository;
        private final RoleRepo roleRepository;
        private final PasswordEncoder passwordEncoder;

        public ApiResponse<User> registration(RegistrationRequest request){
            Optional<User> userByUsername = userRepository.findByUsername(request.getUsername());
            Optional<User> userByEmail = userRepository.findByEmail(request.getEmail());
            if (userByUsername.isPresent() || userByEmail.isPresent()){
                return ApiResponse.error("Username or email already exist");
            }

            //registration logic(USER RULES)
            if (    !request.getFirstname().matches("^[A-Za-z]{3,}$") ||
                    !request.getLastname().matches("^[A-Za-z]{3,}$") ){
                return ApiResponse.error("Name and lastname must contain only letters and be at least 3 characters long.");
            }

            if (!request.getUsername().matches("^(?=.*[0-9])[A-Za-z0-9]{5,}$")){
                return ApiResponse.error("Username must have at least 5 characters and minimum one number.");
            }

            if (!request.getEmail().matches("^[A-Za-z0-9._%+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            ){
                return ApiResponse.error("Please provide a valid email address.");
            }

            if (!request.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9!@#$%^&*]{6,}$")
            ){
                return ApiResponse.error("Password must be at least 6 characters long, contain one uppercase letter and one number.");
            }

            User user = new User(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword())
            );
            Role defaultRole = roleRepository.findById(2).orElseThrow();
            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
            user.setRoles(roles);
            userRepository.save(user);

            return ApiResponse.success("Registration is complete successfully!",user);
        }
}
