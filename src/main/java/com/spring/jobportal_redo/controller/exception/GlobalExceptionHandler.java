package com.spring.jobportal_redo.controller.exception;

import com.spring.jobportal_redo.domain.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RestResponse<Object>> handleNoSuchElementException(NoSuchElementException ex) {
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponse.setError("Error");
        restResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

}
