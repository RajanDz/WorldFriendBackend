package com.example.worldFriend.repository;

import com.example.worldFriend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByCountry(String countryName);
    List<Location> findByCity(String cityName);
}
