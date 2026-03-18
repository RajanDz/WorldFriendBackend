package com.example.worldFriend.repository;

import com.example.worldFriend.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

<<<<<<< Updated upstream
public interface LocationRepository extends JpaRepository<Location, Long> {

=======
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
>>>>>>> Stashed changes
    List<Location> findByCountry(String countryName);
    List<Location> findByCity(String cityName);
}
