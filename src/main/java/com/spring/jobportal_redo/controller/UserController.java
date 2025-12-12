package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.ApiResponse;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import com.spring.jobportal_redo.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    // CREATE user
    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserCreateDto createDto) {
        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));
        return userService.create(createDto);
    }


    // GET all users
    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<User> spec,
            Pageable pageable
    ) {
        return userService.getAll(spec, pageable);
    }

    // GET user by id
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // UPDATE user
    @PutMapping
    public UserResponseDto update(@RequestBody @Valid UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}