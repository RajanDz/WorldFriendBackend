package com.example.worldFriend.repository;

import com.example.worldFriend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query("""
            select u from User u 
            left join fetch u.roles
            where u.username = :username
            """)
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
