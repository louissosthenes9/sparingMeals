package com.example.SparingMeals.Controller;

import com.example.SparingMeals.Config.JwtProvider;
import com.example.SparingMeals.Repository.CartRepository;
import com.example.SparingMeals.Repository.UserRepository;
import com.example.SparingMeals.Response.AuthResponse;
import com.example.SparingMeals.Service.CustomUserDetailsService;
import com.example.SparingMeals.model.Cart;
import com.example.SparingMeals.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartRepository cartRepository;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.cartRepository = cartRepository;
    }


    @PostMapping("/Signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user ) throws Exception {
         User isEmailExist = userRepository.findByEmail(user.getEmail());

         if(isEmailExist !=null){
             throw new Exception("Email is already taken");
         }

         User createdUser = new User();

         createdUser.setEmail(user.getEmail());
         createdUser.setFullName(user.getFullName());
         createdUser.setRole(user.getRole());
         createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

         User savedUser = userRepository.save(createdUser);

         Cart cart = new Cart();
         cart.setCustomer(savedUser);

         cartRepository.save(cart);

         Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
         SecurityContextHolder.getContext().setAuthentication(authentication);

         String jwt = jwtProvider.generateToken(authentication);

         AuthResponse authResponse = new AuthResponse();

          authResponse.setJwt(jwt);
          authResponse.setMessage("Registration successfull");
          authResponse.setRole(savedUser.getRole());

         return  new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

}
