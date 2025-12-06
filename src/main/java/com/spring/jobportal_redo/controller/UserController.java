package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponDto;
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
    public ResponseEntity<List<UserResponDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponDto> getUserById(@PathVariable Long id) {
        UserResponDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // CREATE user
    @PostMapping
    public ResponseEntity<UserResponDto> createUser(@RequestBody @Valid UserCreateDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.createUser(user));
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userDetails) {
        UserResponDto updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}