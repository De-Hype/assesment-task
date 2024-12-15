package com.david.assessment_tasks.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    private String message;
    private HttpStatus status;
    private String details;
    private LocalDateTime timestamp;


    public ErrorResponseDto(String message, HttpStatus status, String details, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.details = details;
        this.timestamp = timestamp;
    }

}
