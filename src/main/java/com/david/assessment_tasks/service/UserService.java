package com.david.assessment_tasks.service;

import com.david.assessment_tasks.dto.LoginDto;
import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(RegisterDto registerDto);

    User loginUser(LoginDto loginDto);

    User updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getAllUsers();
}
