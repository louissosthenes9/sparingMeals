package com.example.SparingMeals.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDTO {

    private String title;

    @Column(length=1000)
    private List<String> images;
    private Long id;


}
