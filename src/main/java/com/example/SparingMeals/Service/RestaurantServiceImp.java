package com.example.SparingMeals.Service;

import com.example.SparingMeals.DTO.RestaurantDTO;
import com.example.SparingMeals.Repository.AddressRepository;
import com.example.SparingMeals.Repository.RestaurantRepository;
import com.example.SparingMeals.Repository.UserRepository;
import com.example.SparingMeals.model.Address;
import com.example.SparingMeals.model.Restaurant;
import com.example.SparingMeals.model.User;
import com.example.SparingMeals.request.CreateRestaurantRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{
    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    public RestaurantServiceImp(RestaurantRepository restaurantRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHrs());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }

        // Update the fields if the new value is not null
        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        if (updatedRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }

        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if (updatedRestaurant.getImages() != null) {
            restaurant.setImages(updatedRestaurant.getImages());
        }

        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }

        if (updatedRestaurant.getOpeningHrs() != null) {
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHrs());
        }

        if (updatedRestaurant.getAddress() != null) {
            Address updatedAddress = addressRepository.save(updatedRestaurant.getAddress());
            restaurant.setAddress(updatedAddress);
        }

        // Update the registration date to the current date and time
        restaurant.setRegistrationDate(LocalDateTime.now());

        // Save and return the updated restaurant
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }

        restaurantRepository.delete(restaurant);


    }

    @Override
    public List<Restaurant> getAllRestaurant() {
       return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {

        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if(opt.isEmpty()){
            throw new Exception("restaurant not found" + restaurantId);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant == null) {
            throw new Exception("Restaurant with owner id:" + " " + userId + "not found");
        }

        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDTO dto = new RestaurantDTO();

        dto.setDescription(restaurant.getDescription());

        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        if(user.getFavourites().contains(dto)){
            user.getFavourites().remove(dto);
        } else {
            user.getFavourites().add(dto);
        }

        userRepository.save(user);

        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);

        if (restaurant == null) {
            throw new Exception("restaurant not found");
        }
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
