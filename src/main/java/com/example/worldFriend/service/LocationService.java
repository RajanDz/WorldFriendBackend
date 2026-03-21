package com.example.worldFriend.service;

import com.example.worldFriend.dto.CreateLocationRequest;
import com.example.worldFriend.dto.SearchFiltersDto;
import com.example.worldFriend.dto.SearchReturnListDto;
import com.example.worldFriend.enums.LocationType;
import com.example.worldFriend.model.City;
import com.example.worldFriend.model.Location;
import com.example.worldFriend.repository.CitiesRepository;
import com.example.worldFriend.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private String defaultPath = "C:/Users/Rajan44/OneDrive/Desktop/worldFriendCoverImgs/";
    private final LocationRepository locationRepository;
    private final CitiesRepository citiesRepository;
    public Location findLocationById(long id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "We cannot find location with provided id"));
    }

    public List<Location> getRecommendedLocations() {
        return locationRepository.findAll(PageRequest.of(0, 5)).getContent();
    }


    public List<Location> findLocationByCityId(Long id){
       List<Location> similarLocations = locationRepository.findByCityId(id);
       if (similarLocations.isEmpty()){
           throw new EntityNotFoundException("No locations found");
       }
       return similarLocations;
    }

    public Location createLocation(CreateLocationRequest locationRequest) {
        City city = citiesRepository.findById(locationRequest.getCityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "We cannot find city with provided id"));
        Location location = new Location(locationRequest.getName(), locationRequest.getDescription(), locationRequest.getType(), locationRequest.getCountry(), locationRequest.getCity(), locationRequest.getLatitude(), locationRequest.getLongitude(),city);
        locationRepository.save(location);
        return location;
    }

    public Location uploadImage(long id, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
        }

        Location location = locationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exist with user id : " + id));

        String original = file.getOriginalFilename();
        String ex = original.substring(original.lastIndexOf("."));
        String uniqueName = java.util.UUID.randomUUID() + ex;
        Path filePath = Paths.get(defaultPath, uniqueName);
        System.out.println(filePath);
        Files.copy(file.getInputStream(), filePath);

        location.updateImgUrl("/" + uniqueName);
        locationRepository.save(location);
        return location;

    }

    public List<Location> findByCountryName(String countryName) {
        List<Location> locations = locationRepository.findByCountry(countryName);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No locations for country name: " + countryName
            );
        }
        return locations;
    }

    public List<Location> findByCityName(String cityName) {
        List<Location> locations = locationRepository.findByCity(cityName);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No locations for city name: " + cityName
            );
        }
        return locations;
    }

    public Page<SearchReturnListDto> getLocationBySearchFilters(SearchFiltersDto searchFiltersDto, Pageable pageable) {
        Specification<Location> query = getFiltersQuery(searchFiltersDto);
        return locationRepository.findAll(query, pageable).map(city -> new SearchReturnListDto(city.getId(),city.getName(),city.getCountry()));

    }

    public Specification<Location> getFiltersQuery(SearchFiltersDto searchFilters){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchFilters.getName() != null && !searchFilters.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + searchFilters.getName().toLowerCase() + "%")
                );
            }
            if (searchFilters.getCountry() != null && !searchFilters.getCountry().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("country"), searchFilters.getCountry()));
            }
            if (searchFilters.getCity() != null && !searchFilters.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), searchFilters.getCity()));
            }
            if (searchFilters.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), LocationType.valueOf(searchFilters.getType().toUpperCase())));
            }

            return predicates.isEmpty() ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
