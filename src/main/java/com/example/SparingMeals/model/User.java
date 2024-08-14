package com.example.SparingMeals.model;

import com.example.SparingMeals.DTO.RestaurantDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ElementCollection
    private List<RestaurantDTO> favourites = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses =  new ArrayList<>();



}
