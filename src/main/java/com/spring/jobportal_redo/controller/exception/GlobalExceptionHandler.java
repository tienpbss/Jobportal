package com.spring.jobportal_redo.controller.exception;

import com.spring.jobportal_redo.domain.RestResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        RestResponse<Object> restResponse = new RestResponse<>();
        List<String> messages = ex.getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        restResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponse.setError("Error");
        restResponse.setMessage(messages);
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

}
