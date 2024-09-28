package com.example.SparingMeals.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class RestaurantDTO {
    @Id
    private Long id;
    private String title;

    private String Description;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;



}
