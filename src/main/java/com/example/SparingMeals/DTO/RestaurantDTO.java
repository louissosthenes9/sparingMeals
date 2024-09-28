package com.example.SparingMeals.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDTO {
    private String title;

    private String Description;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private Long id;

}
