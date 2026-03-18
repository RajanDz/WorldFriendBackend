package com.example.worldFriend.controller;


import com.example.worldFriend.generics.ApiResponse;
import com.example.worldFriend.model.City;
import com.example.worldFriend.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CitiesController {

    private final CitiesService citiesService;

    @GetMapping("/public/recommendedCities")
    public ResponseEntity<ApiResponse<List<City>>> getRecommendedCities(){
        List<City> cityList = citiesService.getRecommendedCities();
        return ResponseEntity.ok(ApiResponse.success("List of cities",cityList));
    }

    @GetMapping("/public/getCityById/{id}")
    public ResponseEntity<ApiResponse<City>> getCityById(@PathVariable(name = "id") Long id){
        City city = citiesService.getCityById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully", city));
    }
    @GetMapping("/public/cities")
    public ResponseEntity<ApiResponse<List<City>>> searchCities(@RequestParam(name = "name") String name){
        List<City> cityList = citiesService.findCitiesBySearchParamters(name);
        return ResponseEntity.ok(ApiResponse.success("Successfully", cityList));
    }
    @PostMapping("/uploadImg")
    public ResponseEntity<ApiResponse<City>> uploadCoverImg(@RequestParam(name = "id") Long id, @RequestParam(name = "cover_img")MultipartFile file) throws IOException {
        City updatedCity = citiesService.uploadImg(id,file);
        return ResponseEntity.ok(ApiResponse.success("Img is uploaded", updatedCity));
    }
}
