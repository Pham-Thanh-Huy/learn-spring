package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.UserDto;
import com.example.learning_spring.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getName());
        return userDto;
    }
}
