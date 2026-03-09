package com.example.worldFriend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "cities")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;


    @NonNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NonNull
    @Column(name = "country")
    private String country;

    @NonNull
    @Column(name = "type")
    private String type;

    @Column(name = "cover_img")
    private String coverImageUrl;

    public void updateCityImg(String coverImgUrl){
        this.coverImageUrl = coverImgUrl;
    }
}
