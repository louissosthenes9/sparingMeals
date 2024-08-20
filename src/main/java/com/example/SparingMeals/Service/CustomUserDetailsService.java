package com.example.SparingMeals.Service;

import com.example.SparingMeals.Repository.UserRepository;
import com.example.SparingMeals.model.USER_ROLE;
import com.example.SparingMeals.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user ==null){
            throw new UsernameNotFoundException("user not found with email");
        }

        USER_ROLE role = user.getRole();

        if(role != null){
            role = USER_ROLE.ROLE_CUSTOMER;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));



        return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
