package com.example.worldFriend.model;

import com.example.worldFriend.enums.LocationType;
import jakarta.persistence.*;
import lombok.*;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "description")
    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NonNull
    private LocationType type;

    @Column(name = "country")
    @NonNull
    private String country;

    @Column(name = "city")
    @NonNull
    private String cityName;

    @Column(name = "latitude")
    @NonNull
    private Double latitude;

    @Column(name = "longitude")
    @NonNull
    private Double longitude;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    public void updateImgUrl(String coverImageUrl){
        this.coverImageUrl = coverImageUrl;
    }

}
