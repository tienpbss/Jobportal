package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponDto;
import com.spring.jobportal_redo.repository.UserRepository;
import com.spring.jobportal_redo.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public UserResponDto createUser(UserCreateDto userInfo) {
        if (userRepository.existsByEmail(userInfo.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = userMapper.toEntity(userInfo);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}