package com.example.SparingMeals.Controller;

import com.example.SparingMeals.Service.UserService;
import com.example.SparingMeals.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) {
    User user;
    try {
        user = userService.findUserByJwtToken(jwt);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
 }
}
