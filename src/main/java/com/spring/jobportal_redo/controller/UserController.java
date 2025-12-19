package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.user.*;
import com.spring.jobportal_redo.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegisterDto dto) {
        return userService.register(dto);
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
    // GET user by id
    @GetMapping("/get/info")
    public UserResponseDto getInfoUserLogin() {
        return userService.getInfoUserLogin();
    }


    // UPDATE user
    @PutMapping
    public UserResponseDto update(@RequestBody @Valid UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    @PutMapping("/edit-info")
    public UserResponseDto editInfo(@RequestBody @Valid UserEditInfoDto editDto) {
        return userService.editInfo(editDto);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}