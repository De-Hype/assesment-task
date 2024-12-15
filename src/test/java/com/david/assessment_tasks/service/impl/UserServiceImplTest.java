package com.david.assessment_tasks.service.impl;

import com.david.assessment_tasks.dto.LoginDto;
import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;
import com.david.assessment_tasks.exception.EmailAlreadyExistException;
import com.david.assessment_tasks.exception.InvalidCredentialsException;
import com.david.assessment_tasks.exception.UserNotFoundException;
import com.david.assessment_tasks.mappers.RegisterDtoMapper;
import com.david.assessment_tasks.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterDto registerDto;
    private User user;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto(
            "John", 
            "Doe", 
            "johndoe", 
            "john.doe@example.com", 
            "password123"
        );

        user = new User();
        user.setId("1");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void registerUser_Success() {
        // Arrange
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User registeredUser = userService.registerUser(registerDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(user.getId(), registeredUser.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(EmailAlreadyExistException.class, () -> {
            userService.registerUser(registerDto);
        });
    }

    @Test
    void loginUser_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("johndoe", "password123");
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        // Act
        User loggedInUser = userService.loginUser(loginDto);

        // Assert
        assertNotNull(loggedInUser);
        assertNull(loggedInUser.getPassword()); // Ensure password is nullified
    }

    @Test
    void loginUser_InvalidCredentials() {
        // Arrange
        LoginDto loginDto = new LoginDto("johndoe", "wrongpassword");
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> {
            userService.loginUser(loginDto);
        });
    }

    @Test
    void updateUser_Success() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setFirstName("Jane");
        userDto.setLastName("Smith");
        userDto.setUsername("janesmith");
        userDto.setEmail("jane.smith@example.com");
        userDto.setPassword("newpassword");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser("1", userDto);

        // Assert
        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("Smith", updatedUser.getLastName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        // Arrange
        UserDto userDto = new UserDto();
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser("1", userDto);
        });
    }

    @Test
    void deleteUser_Success() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser("1");

        // Assert
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_NotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser("1");
        });
    }

    @Test
    void getAllUsers_Success() {
        // Arrange
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDto> userDtos = userService.getAllUsers();

        // Assert
        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
    }
}