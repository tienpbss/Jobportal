package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.dto.ApiResponse;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import com.spring.jobportal_redo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    // GET all users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    // GET user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // CREATE user
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> create(@RequestBody @Valid UserCreateDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserResponseDto userResponseDto = userService.create(user);
        ApiResponse<UserResponseDto> response = ApiResponse.success("Created user", userResponseDto);
        return ResponseEntity.ok(response);
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userDetails) {
        UserResponseDto updatedUser = userService.update(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}