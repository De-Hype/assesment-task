package com.david.assessment_tasks.service.impl;

import com.david.assessment_tasks.dto.LoginDto;
import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;
import com.david.assessment_tasks.exception.EmailAlreadyExistException;
import com.david.assessment_tasks.exception.InvalidCredentialsException;
import com.david.assessment_tasks.exception.UserNotFoundException;
import com.david.assessment_tasks.mappers.RegisterDtoMapper;
// import com.david.assessment_tasks.mapper.RegisterDtoMapper;
import com.david.assessment_tasks.repository.UserRepository;
import com.david.assessment_tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RegisterDtoMapper registerDtoToUserMapper = new RegisterDtoMapper();

    @Override
    public User registerUser(RegisterDto registerDto) {

        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistException("Email is already registered");
        }

        User user = registerDtoToUserMapper.registerDtoToUser(registerDto);

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User loginUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {

            User sanitizedUser = user.get();
            sanitizedUser.setPassword(null);
            return sanitizedUser;
        }

        // If credentials are invalid, throw an exception
        throw new InvalidCredentialsException("Invalid username or password");
    }


    @Override
    public User updateUser(String userId, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());

            // Update password only if provided
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            return userRepository.save(user);
        }

        throw new UserNotFoundException("User not found");
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Map users to UserDto and exclude the password
        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setUsername(user.getUsername());
                    userDto.setPassword(user.getPassword());
                    userDto.setEmail(user.getEmail());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

}
