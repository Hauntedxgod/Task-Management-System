package com.example.testeffectivemobile.service;

import com.example.testeffectivemobile.exceptions.UserNotFoundException;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public PersonDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try{
            user = userService.findByEmail(username);
        } catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }
        return new PersonDetails(user);
    }
}
