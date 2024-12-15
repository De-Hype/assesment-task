package com.david.assessment_tasks.mappers;

import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;

public class UserDtoMapper {
    public static UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
