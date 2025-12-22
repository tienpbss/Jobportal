package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.Role;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.user.*;
import com.spring.jobportal_redo.repository.UserRepository;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.exception.UnAuthorizationException;
import com.spring.jobportal_redo.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyService companyService;
    private final RoleService roleService;

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

    public UserResponseDto getInfoUserLogin() {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        User user = getByEmail(email);
        return userMapper.toResponse(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with email"));
    }

    public UserResponseDto create(UserCreateDto createDto) {
        checkEmailExists(createDto.getEmail());
        Company company = (createDto.getCompanyId() == null)
                ? null
                : companyService.getCompanyByIdOrThrow(createDto.getCompanyId());
        Role role = (createDto.getRoleId() == null)
                ? null
                : roleService.getRoleByIdOrThrow(createDto.getRoleId());

        User user = userMapper.toUser(createDto);
        if (company != null) {
            user.assignCompany(company);
        }
        if (role != null) {
            user.assignRole(role);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDto register(UserRegisterDto registerDto) {
        checkEmailExists(registerDto.getEmail());
        User user = userMapper.toUser(registerDto);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDto update(UserUpdateDto updateDto) {
        User user = getUserByIdOrThrow(updateDto.getId());
        Company company = (updateDto.getCompanyId() == null)
                ? null
                : companyService.getCompanyByIdOrThrow(updateDto.getCompanyId());
        Role role = (updateDto.getRoleId() == null)
                ? null
                : roleService.getRoleByIdOrThrow(updateDto.getRoleId());
        userMapper.updateEntityFromDto(updateDto, user);

        if (company != null) {
            user.setCompany(company);
        }
        if (role != null) {
            user.setRole(role);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDto editInfo(UserEditInfoDto dto) {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        User user = getUserByEmailOrThrow(email);
        userMapper.updateEntityFromDto(dto, user);
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

    public User getUserByEmailOrThrow(String email) {
        return userRepository.getByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email"));
    }

    public User getUserLogin() {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        return getByEmail(email);
    }

    public boolean userLoginHasPermission(String urlPattern, String method) {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");


        if (email.equals("anonymousUser")) {
            //If user not login, no need interceptor because it will be blocked by spring security first
            return true;
        }

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UnAuthorizationException("User not found with email inside token")
        );
        return Optional.ofNullable(user.getRole())
                .map(Role::getPermissions)
                .orElse(Collections.emptySet())
                .stream()
                .anyMatch(p -> p.getApiPath().equals(urlPattern) && p.getMethod().equals(method));
    }
}