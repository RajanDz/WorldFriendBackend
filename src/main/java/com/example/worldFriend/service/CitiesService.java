package com.example.worldFriend.service;


import com.example.worldFriend.dto.SearchFiltersDto;
import com.example.worldFriend.model.City;
import com.example.worldFriend.repository.CitiesRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.criteria.Predicate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitiesService {

    @Value("${APP_DEFAULT_PATH}")
    private String defaultPath;

    private final CitiesRepository citiesRepository;


    public List<City> getRecommendedCities(){
        return citiesRepository.findAll(PageRequest.of(0,5)).getContent();
    }
    public City getCityById(Long id){
        return citiesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "We can not find city with provided id"));
    }


    public List<City> findCitiesBySearchParamters(String name) {
        return citiesRepository.findByNameContainingIgnoreCase(name);
    }

    public City uploadImg(Long id, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Image file is required");
        }

        City city = citiesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"We can not find city with provided id."));


        String original = file.getOriginalFilename();
        String ex = original.substring(original.lastIndexOf("."));
        String uniqueName = java.util.UUID.randomUUID() + ex;
        Path filePath = Paths.get(defaultPath,uniqueName);
        System.out.println(filePath);
        Files.copy(file.getInputStream(),filePath);

        city.updateCityImg("/" + uniqueName);
        citiesRepository.save(city);
        return city;
    }

    public Page<City> getCitiesBySearchFilters(SearchFiltersDto searchFiltersDto, Pageable pageable){
        Specification<City> query = searchCitiesList(searchFiltersDto);
        return citiesRepository.findAll(query,pageable);
    }

    public Specification<City> searchCitiesList(SearchFiltersDto searchFiltersDto){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchFiltersDto.getName() != null && !searchFiltersDto.getName().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+searchFiltersDto.getName().toLowerCase()+"%"));
            }
            if (searchFiltersDto.getCountry() != null && !searchFiltersDto.getCountry().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("country"),searchFiltersDto.getCountry()));
            }
            if (searchFiltersDto.getType() != null && !searchFiltersDto.getType().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("type"), searchFiltersDto.getType()));
            }

            return predicates.isEmpty() ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
