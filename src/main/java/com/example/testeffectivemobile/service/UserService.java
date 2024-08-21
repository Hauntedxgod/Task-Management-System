package com.example.testeffectivemobile.service;

import com.example.testeffectivemobile.dto.UserDto;
import com.example.testeffectivemobile.exceptions.UserNotFoundException;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException:: new);
    }


    public void save(User user){
        userRepository.save(user);
    }


    public User findById(Long id){
        Optional<User> getId = userRepository.findById(id);
        return getId.orElseThrow(UserNotFoundException::new);
    }


    public void updatePerson(Long id , UserDto userDto){
        userDto.setId(id);
        userDto.setEmail(userDto.getEmail());
        userDto.setPassword(userDto.getPassword());
        save(modelMapper.map(userDto , User.class));
    }


    public void createUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
