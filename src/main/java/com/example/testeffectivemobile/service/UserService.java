package com.example.testeffectivemobile.service;

import com.example.testeffectivemobile.dto.AuthDto;
import com.example.testeffectivemobile.exceptions.UserNotCreatedException;
import com.example.testeffectivemobile.exceptions.UserNotFoundException;
import com.example.testeffectivemobile.exceptions.UserNotUpdateException;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.UserRepository;
import com.example.testeffectivemobile.security.PersonDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;




    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException:: new);
    }

    public User getInfoMyUser(PersonDetails personDetails){
        return userRepository.findByEmail(personDetails.getUsername()).orElseThrow(UserNotFoundException::new);
    }


    public void save(User user){
        userRepository.save(user);
    }


    public User findById(Long id){
        Optional<User> getId = userRepository.findById(id);
        return getId.orElseThrow(UserNotFoundException::new);
    }


    public void updatePerson(Long id , AuthDto authDto){
        User user = findById(id);
        user.setEmail(authDto.getEmail());
        user.setPassword(authDto.getPassword());
        save(user);
    }


    public void createUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void userNotCreated(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new UserNotCreatedException(builder.toString());

        }
    }

        public void userNotUpdate(BindingResult result) {
            if (result.hasErrors()) {

                StringBuilder builder = new StringBuilder();

                List<FieldError> fieldErrors = result.getFieldErrors();
                fieldErrors.forEach(error -> {
                    builder.append(error.getField());
                    builder.append("-");
                    builder.append(error.getDefaultMessage());
                });
                throw new UserNotUpdateException(builder.toString());

            }


    }

}
