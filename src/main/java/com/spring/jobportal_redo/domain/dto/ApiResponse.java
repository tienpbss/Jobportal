package com.spring.jobportal_redo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer status;
    private String message;
    private T data;
    private Instant timestamp;

    public  ApiResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = Instant.now();
    }
    public  ApiResponse(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

}
