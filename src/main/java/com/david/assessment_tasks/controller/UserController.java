package com.david.assessment_tasks.controller;

import com.david.assessment_tasks.constants.UserConstant;
import com.david.assessment_tasks.dto.LoginDto;
import com.david.assessment_tasks.dto.RegisterDto;
import com.david.assessment_tasks.dto.ResponseDto;
import com.david.assessment_tasks.dto.UserDto;
import com.david.assessment_tasks.entity.User;
import com.david.assessment_tasks.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/users", produces ={MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        userService.registerUser(registerDto);

        ResponseDto responseDto = new ResponseDto(
                UserConstant.StatusCode.CREATED.getCode(),
                UserConstant.getMessageForStatus(UserConstant.StatusCode.CREATED)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
        userService.loginUser(loginDto);


        ResponseDto responseDto = new ResponseDto(
                UserConstant.StatusCode.OK.getCode(),
                UserConstant.getMessageForStatus(UserConstant.StatusCode.OK)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);

        ResponseDto responseDto = new ResponseDto(
                UserConstant.StatusCode.OK.getCode(),
                UserConstant.USER_UPDATED
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        ResponseDto responseDto = new ResponseDto(
                UserConstant.StatusCode.NO_CONTENT.getCode(),
                UserConstant.getMessageForStatus(UserConstant.StatusCode.NO_CONTENT)
        );

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtos = userService.getAllUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }

}
