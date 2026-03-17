package com.example.worldFriend.controller;


import com.example.worldFriend.dto.AuthResponse;
import com.example.worldFriend.dto.RegistrationRequest;
import com.example.worldFriend.dto.SigninDto;
import com.example.worldFriend.generics.ApiResponse;
import com.example.worldFriend.model.User;
import com.example.worldFriend.security.CustomUserDetails;
import com.example.worldFriend.security.jwt.JwtUtils;
import com.example.worldFriend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final AuthService authService;
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<ResponseCookie>> signin(@RequestBody SigninDto signinRequest){

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User principal: {}", authentication);

            ResponseCookie authCookie = jwtUtils.generateAuthCookie(authentication);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authCookie.toString()).body(ApiResponse.success("Successfully logged in", authCookie));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody RegistrationRequest request){
        ApiResponse<User> registrationProcces = authService.registration(request);
        return ResponseEntity.ok(registrationProcces);
    }
}
