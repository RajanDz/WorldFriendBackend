package com.example.worldFriend.controller;


import com.example.worldFriend.dto.SigninDto;
import com.example.worldFriend.security.CustomUserDetails;
import com.example.worldFriend.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/signin")
    public String signin(@RequestBody SigninDto signinRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User principal: {}", authentication);

            String token = jwtUtils.generateToken(authentication);

            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
