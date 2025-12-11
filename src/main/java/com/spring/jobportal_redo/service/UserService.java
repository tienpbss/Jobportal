package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import com.spring.jobportal_redo.repository.UserRepository;
import com.spring.jobportal_redo.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseList(users);
    }

    public UserResponseDto getById(Long id) {
        User user = getUserOrThrow(id);
        return userMapper.toResponse(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with email"));
    }

    public UserResponseDto create(UserCreateDto userInfo) {
        if (userRepository.existsByEmail(userInfo.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = userMapper.toEntity(userInfo);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDto update(Long id, UserUpdateDto userDetails) {
        User user = getUserOrThrow(id);
        userMapper.updateEntityFromDto(userDetails, user);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public void deleteUser(Long id) {
        User user = getUserOrThrow(id);
        userRepository.delete(user);
    }

    public User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public void saveRefreshToken(User user, String token) {
        user.setRefreshToken(token);
        userRepository.save(user);
    }

    public boolean checkRefreshTokenExists(String refreshToken) {
        return userRepository.existsByRefreshToken(refreshToken);
    }

}