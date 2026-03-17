package com.example.worldFriend.security.jwt;

import com.example.worldFriend.repository.UserRepo;
import com.example.worldFriend.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private long expirationMs;

    @Value("${app.authCookieName}")
    private String authCookieName;

    private final UserRepo userRepository;
    private final UserService userService;
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public ResponseCookie generateAuthCookie(Authentication authentication){
        String jwt = generateToken(authentication);
        return ResponseCookie.from(authCookieName,jwt)
                .path("/")
                .secure(false)
                .sameSite("Lax")
                .maxAge(30 * 60)
                .httpOnly(true)
                .build();
    }

    public String generateToken(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        List<String> authorities = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                                .toList();
        logger.info("auth roles: {}", authentication.getAuthorities());
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles: ",authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key())
                .compact();

    }


    public String getUsernameFromToken(String token){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }


    public  String getJwtFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,authCookieName );
        if (cookie == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"We cannot find authorization token for this request");
        }
        return cookie.getValue();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
