package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import com.spring.jobportal_redo.repository.CompanyRepository;
import com.spring.jobportal_redo.repository.UserRepository;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;

    public PagingReturnDto getAll(Specification<User> spec, Pageable pageable) {
        Page<User> page = userRepository.findAll(spec, pageable);
        MetaPaging mt = MetaPaging.builder()
                .size(pageable.getPageSize())
                .page(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
        List<UserResponseDto> responseDtoList = userMapper.toResponseList(page.getContent());
        return PagingReturnDto.builder()
                .meta(mt)
                .result(responseDtoList)
                .build();
    }

    public UserResponseDto getById(Long id) {
        User user = getUserByIdOrThrow(id);
        return userMapper.toResponse(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with email"));
    }

    public UserResponseDto create(UserCreateDto createDto) {
        checkEmailExists(createDto.getEmail());
        Company company = (createDto.getCompanyId() == null)
                ? null
                : companyRepository.findById(createDto.getCompanyId()).orElseThrow(() -> new NoSuchElementException("Company not found"));

        User user = userMapper.toUser(createDto);
        if (company != null) {
            user.assignCompany(company);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDto update(UserUpdateDto updateDto) {
        User user = getUserByIdOrThrow(updateDto.getId());
        userMapper.updateEntityFromDto(updateDto, user);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public void deleteUser(Long id) {
        User user = getUserByIdOrThrow(id);
        userRepository.delete(user);
    }

    public User getUserByIdOrThrow(Long id) {
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

    public void checkEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public User getUserLogin() {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        return getByEmail(email);
    }

}