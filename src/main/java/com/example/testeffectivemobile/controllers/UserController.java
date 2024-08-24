package com.example.testeffectivemobile.controllers;

import com.example.testeffectivemobile.dto.AuthDto;
import com.example.testeffectivemobile.dto.UserDto;
import com.example.testeffectivemobile.jwt.JWTUtil;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.security.PersonDetails;
import com.example.testeffectivemobile.service.UserEncoderService;
import com.example.testeffectivemobile.service.UserService;
import com.example.testeffectivemobile.validation.UserValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    private final UserEncoderService userEncoderService;

    private final JWTUtil jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, UserValidator userValidator, UserEncoderService userEncoderService, JWTUtil jwtUtils, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userEncoderService = userEncoderService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public Map<String , String> userRegistration(@RequestBody @Valid AuthDto userDto , BindingResult bindingResult){

        User user = modelMapper.map(userDto , User.class);

        userValidator.validate(user , bindingResult);

        if (bindingResult.hasErrors()){
            return Map.of("message" , "500");
        }

        userEncoderService.encoderUser(user);

        String token = jwtUtils.generateToken(user.getEmail());

        return Map.of("jwt-token" , token);

    }

    @PostMapping("/login")
    public Map<String , Object> userRegister(@RequestBody AuthDto userDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getEmail() , userDto.getPassword());

        try{
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (AuthenticationException e){
            return Map.of("message" , "500");
        }

        User userToken = userService.findByEmail(userDto.getEmail());

        String token = jwtUtils.generateToken(userDto.getEmail());

        return Map.of("name" , userToken.getEmail() , "jwt - token" , token );

    }


    @GetMapping("/showAboutMe")
    public UserDto getInfoUser(@AuthenticationPrincipal PersonDetails personDetails){
        return modelMapper.map(userService.getInfoMyUser(personDetails) , UserDto.class);
    }


    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PostMapping("/newUser")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid AuthDto userDto , BindingResult bindingResult){

        userService.userNotCreated(bindingResult);

        User user = modelMapper.map(userDto , User.class);
        user.setPassword(userEncoderService.savePassword(user.getPassword()));
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.fromEntity(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id , @RequestBody @Valid AuthDto authDto ,
                                              BindingResult bindingResult){

        userService.userNotUpdate(bindingResult);

        authDto.setPassword(userEncoderService.savePassword(authDto.getPassword()));
        userService.updatePerson(id , authDto);
        User user = modelMapper.map(authDto , User.class);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
