package com.example.worldFriend.service;

import com.example.worldFriend.dto.CreateLocationRequest;
import com.example.worldFriend.model.Location;
import com.example.worldFriend.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private String defaultPath = "C:/Users/Rajan44/OneDrive/Desktop/worldFriendCoverImgs/";
    private final LocationRepository locationRepository;

    public Location createLocation(CreateLocationRequest locationRequest){
        Location location = new Location(locationRequest.getName(),locationRequest.getDescription(),locationRequest.getType(),locationRequest.getCountry(),locationRequest.getCity(),locationRequest.getLatitude(),locationRequest.getLongitude());
        locationRepository.save(location);
        return location;
    }
    public Location uploadImage(long id,MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image file is required");
        }

        Location location = locationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No user exist with user id : " + id));

        String original = file.getOriginalFilename();
        String ex = original.substring(original.lastIndexOf("."));
        String uniqueName = java.util.UUID.randomUUID() + ex;
        Path filePath = Paths.get(defaultPath,uniqueName);
        System.out.println(filePath);
        Files.copy(file.getInputStream(),filePath);

        location.updateImgUrl("/" + uniqueName);
        locationRepository.save(location);
        return location;

    }
    public List<Location> findByCountryName(String countryName){
        List<Location> locations = locationRepository.findByCountry(countryName);
        if (locations.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No locations for country name: " + countryName
            );
        }
        return locations;
    }

    public List<Location> findByCityName(String cityName){
        List<Location> locations = locationRepository.findByCity(cityName);
        if (locations.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No locations for city name: " + cityName
            );
        }
        return locations;
    }
}
