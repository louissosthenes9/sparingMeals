package com.example.SparingMeals.request;

import com.example.SparingMeals.model.Address;
import com.example.SparingMeals.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private  String  openingHrs;
    private List<String> images;




}
