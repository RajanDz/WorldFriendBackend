package com.example.worldFriend.security.jwt;

import com.example.worldFriend.security.CustomUserDetails;
import com.example.worldFriend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private static Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                logger.info("Auth object before setting up auth context: {}", SecurityContextHolder.getContext().getAuthentication());
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails user =  userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Auth object after setting up auth context: {}", SecurityContextHolder.getContext().getAuthentication());

            }
        } catch (Exception e) {
            logger.warn("Cannot set authentication", e.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
