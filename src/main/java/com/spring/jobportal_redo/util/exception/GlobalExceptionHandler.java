package com.spring.jobportal_redo.util.exception;

import com.spring.jobportal_redo.domain.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, String.join(",", errors));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> IgnoreApiNotFoundException(NoResourceFoundException ex){
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({
            UnAuthorizationException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleUnAuthorizationException(UnAuthorizationException ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllException(Exception ex) {
        logger.error("An error occurred during file operation: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        return ResponseEntity.internalServerError().body(response);
    }
}
