package com.example.worldFriend.controller;


import com.example.worldFriend.model.Location;
import com.example.worldFriend.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationsController {

        private final LocationRepository locationRepository;


        @GetMapping("/defaultLocation")
        @PreAuthorize("hasRole('LOC_ADMIN')")
        public Location getLocation(){
            return locationRepository.findById(1L).orElseThrow();
        }

}
