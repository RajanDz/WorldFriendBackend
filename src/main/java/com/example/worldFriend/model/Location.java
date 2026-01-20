package com.example.worldFriend.model;

import com.example.worldFriend.enums.LocationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "locations",
        indexes = {
                @Index(name = "country_idx", columnList = "country"),
                @Index(name = "city_idx", columnList = "city")
        }
)
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LocationType type;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public static Location create(
            String name,
            String description,
            LocationType type,
            String country,
            double latitude,
            double longitude,
            String city
    ) {
        Location location = new Location();
        location.name = name;
        location.description = description;
        location.type = type;
        location.country = country;
        location.latitude = latitude;
        location.longitude = longitude;
        location.city = city;
        return location;
    }
}
