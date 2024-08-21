package com.example.testeffectivemobile.validation;

import com.example.testeffectivemobile.exceptions.UserNotFoundException;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public UserValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try{
            personDetailsService.loadUserByUsername(user.getEmail());
        }catch (UserNotFoundException e){
            return;
        }
        errors.rejectValue("name" , "100" , "User with this email exists");
    }

}
