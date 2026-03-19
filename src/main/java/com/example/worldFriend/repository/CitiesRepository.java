package com.example.worldFriend.repository;

import com.example.worldFriend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CitiesRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {


    List<City> findByNameContainingIgnoreCase(String name);
}
