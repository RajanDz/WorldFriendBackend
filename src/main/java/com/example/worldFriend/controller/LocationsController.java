package com.example.worldFriend.controller;


import com.example.worldFriend.dto.CreateLocationRequest;
import com.example.worldFriend.generics.ApiResponse;
import com.example.worldFriend.model.Location;
import com.example.worldFriend.repository.LocationRepository;
import com.example.worldFriend.service.LocationService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationsController {

        private final LocationService locationService;

        @GetMapping("/public/findLocationById/{id}")
        public ResponseEntity<ApiResponse<Location>> getLocationsById(@PathVariable long id){
            Location location = locationService.findLocationById(id);
            return  ResponseEntity.ok(ApiResponse.success("We found location you are searching for", location));
        }

        @PostMapping("/createLocation")
        public ResponseEntity<ApiResponse<Location>> createLocation(@Valid @RequestBody CreateLocationRequest request) {
            Location locationMethod = locationService.createLocation(request);
            return ResponseEntity.ok(ApiResponse.success("Created", locationMethod));
        }

        @PostMapping("/uploadImg")
        public ResponseEntity<ApiResponse<Location>> uploadImg(
                @RequestParam(name = "id") long id
                ,@RequestParam(name = "img") MultipartFile file) throws IOException {

            Location uploadImg = locationService.uploadImage(id,file);
            return ResponseEntity.ok(ApiResponse.success("Image is uploaded", uploadImg));
        }

        @GetMapping("/findByCountry/{country}")
        public ResponseEntity<ApiResponse<List<Location>>> findByCountry(@PathVariable(name = "country") String country){
                List<Location> locationByCountryMethod = locationService.findByCountryName(country);
                return ResponseEntity.ok(ApiResponse.success("Found locations by country name: ", locationByCountryMethod));
        }

        @GetMapping("/findByCity/{city}")
        public ResponseEntity<ApiResponse<List<Location>>> findByCity(@PathVariable(name = "city")String cityName){
            List<Location> locations = locationService.findByCityName(cityName);
            return ResponseEntity.ok(ApiResponse.success("Found locations by city name: ", locations));
        }

        @GetMapping("/public/getRecommendedLocations")
        public ResponseEntity<ApiResponse<List<Location>>> getRecommendedLocations(){
            List<Location> locations = locationService.getRecommendedLocations();
            return ResponseEntity.ok(ApiResponse.success("Found 5 recommended locations.", locations));
        }
}
