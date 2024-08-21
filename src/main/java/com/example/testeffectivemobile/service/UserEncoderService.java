package com.example.testeffectivemobile.service;

import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEncoderService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserEncoderService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String savePassword(String input){
        return passwordEncoder.encode(input);
    }

    public void encoderUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
