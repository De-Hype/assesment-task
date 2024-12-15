package com.david.assessment_tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    // Manually added constructor to initialize fields
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {

    }




    // Manually added getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
