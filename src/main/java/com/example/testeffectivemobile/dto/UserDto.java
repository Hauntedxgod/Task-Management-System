package com.example.testeffectivemobile.dto;

import com.example.testeffectivemobile.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String password;


    public static UserDto fromEntity(User user){
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
