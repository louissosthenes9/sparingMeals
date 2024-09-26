package com.example.SparingMeals.Service;

import com.example.SparingMeals.model.User;

public interface UserService  {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
