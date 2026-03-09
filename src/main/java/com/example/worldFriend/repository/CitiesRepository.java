package com.example.worldFriend.repository;

import com.example.worldFriend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitiesRepository extends JpaRepository<City, Long> {


    List<City> findByNameContainingIgnoreCase(String name);
}
