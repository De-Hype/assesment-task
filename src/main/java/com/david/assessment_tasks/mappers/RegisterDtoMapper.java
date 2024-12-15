package com.david.assessment_tasks.mappers;

import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.entity.User;

import java.time.LocalDateTime;

public class RegisterDtoMapper {

    public static User registerDtoToUser(RegisterDto registerDto) {
        if (registerDto == null) {
            return null;
        }

        // Create a new User instance and set values from RegisterDto
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());  // Password should be encrypted before saving
        user.setCreatedAt(LocalDateTime.now());  // Set the current time as createdAt

        return user;
    }
}
