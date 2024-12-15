package com.david.assessment_tasks.integration;

import com.david.assessment_tasks.AssessmentTasksApplication;
import com.david.assessment_tasks.dto.LoginDto;
import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;
import com.david.assessment_tasks.exception.EmailAlreadyExistException;
import com.david.assessment_tasks.exception.InvalidCredentialsException;
import com.david.assessment_tasks.exception.UserNotFoundException;
import com.david.assessment_tasks.repository.UserRepository;
import com.david.assessment_tasks.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AssessmentTasksApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        userRepository.deleteAll();

        registerDto = new RegisterDto(
            "John", 
            "Doe", 
            "johndoe", 
            "john.doe@example.com", 
            "password123"
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void registerUser_FullIntegrationFlow() {
        // Register User
        User registeredUser = userService.registerUser(registerDto);

        // Verify registration
        assertNotNull(registeredUser);
        assertEquals(registerDto.getUsername(), registeredUser.getUsername());
        assertEquals(registerDto.getEmail(), registeredUser.getEmail());
        assertNotNull(registeredUser.getId());
        assertNotNull(registeredUser.getCreatedAt());
    }

    @Test
    void registerUser_DuplicateEmail() {
        // First registration
        userService.registerUser(registerDto);

        // Try registering with same email
        RegisterDto duplicateDto = new RegisterDto(
            "Jane", 
            "Doe", 
            "janedoe", 
            "john.doe@example.com", 
            "password456"
        );

        assertThrows(EmailAlreadyExistException.class, () -> {
            userService.registerUser(duplicateDto);
        });
    }

    @Test
    void loginUser_SuccessfulLogin() {
        // First register the user
        userService.registerUser(registerDto);

        // Then try to login
        LoginDto loginDto = new LoginDto(
            registerDto.getUsername(), 
            registerDto.getPassword()
        );

        User loggedInUser = userService.loginUser(loginDto);

        assertNotNull(loggedInUser);
        assertEquals(registerDto.getUsername(), loggedInUser.getUsername());
        assertNull(loggedInUser.getPassword()); // Ensure password is not returned
    }

    @Test
    void loginUser_InvalidCredentials() {
        // First register the user
        userService.registerUser(registerDto);

        // Try login with wrong password
        LoginDto invalidLoginDto = new LoginDto(
            registerDto.getUsername(), 
            "wrongpassword"
        );

        assertThrows(InvalidCredentialsException.class, () -> {
            userService.loginUser(invalidLoginDto);
        });
    }

    @Test
    void updateUser_SuccessfulUpdate() {
        // First register and get the user
        User registeredUser = userService.registerUser(registerDto);

        // Prepare update DTO
        UserDto updateDto = new UserDto();
        updateDto.setFirstName("Jane");
        updateDto.setLastName("Smith");
        updateDto.setUsername("janesmith");
        updateDto.setEmail("jane.smith@example.com");

        // Update user
        User updatedUser = userService.updateUser(registeredUser.getId(), updateDto);

        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("Smith", updatedUser.getLastName());
        assertEquals("janesmith", updatedUser.getUsername());
    }

    @Test
    void getAllUsers_MultipleUsers() {
        // Register multiple users
        userService.registerUser(registerDto);

        RegisterDto secondUserDto = new RegisterDto(
            "Jane", 
            "Smith", 
            "janesmith", 
            "jane.smith@example.com", 
            "password456"
        );
        userService.registerUser(secondUserDto);

        // Get all users
        List<UserDto> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
    }
}