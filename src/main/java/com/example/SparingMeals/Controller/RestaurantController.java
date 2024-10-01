package com.example.SparingMeals.Controller;

import com.example.SparingMeals.Service.RestaurantService;
import com.example.SparingMeals.Service.UserService;
import com.example.SparingMeals.model.Restaurant;
import com.example.SparingMeals.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> createRestaurant(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }


    @GetMapping("")

    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurants,HttpStatus.OK);

    }

   @GetMapping("/{id}")
   public ResponseEntity<Restaurant> findRestaurantById(
           @RequestHeader("Authorization") String jwt,
           @PathVariable Long id
   ) throws Exception {
      User user = userService.findUserByJwtToken(jwt);

      Restaurant restaurant = restaurantService.findRestaurantById(id);

      return new ResponseEntity<>(restaurant,HttpStatus.OK);
   }



}