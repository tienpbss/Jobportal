package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Permission;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionCreateDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionResponseDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionUpdateDto;
import com.spring.jobportal_redo.repository.PermissionRepository;
import com.spring.jobportal_redo.util.mapper.PermissionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponseDto create(@Valid PermissionCreateDto dto) {
        checkExistsByApiPathAndMethod(dto.getApiPath(), dto.getMethod());

        Permission permission = permissionMapper.toPermission(dto);
        Permission savedPermission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponseDto(savedPermission);
    }

    public PermissionResponseDto getById(Long id) {
        Permission permission = getPermissionByIdOrThrow(id);
        return permissionMapper.toPermissionResponseDto(permission);
    }

    public PagingReturnDto getAll(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> page = permissionRepository.findAll(spec, pageable);
        MetaPaging mt = new MetaPaging(page.getNumber() +1, page.getSize(), page.getTotalPages(), page.getTotalElements());
        List<PermissionResponseDto> permissionResponseDtoList = permissionMapper.toPermissionResponseDtoList(page.getContent());
        return new PagingReturnDto(mt, permissionResponseDtoList);
    }

    public PermissionResponseDto update(PermissionUpdateDto dto) {
        Permission permission = getPermissionByIdOrThrow(dto.getId());
        if (Objects.equals(dto.getApiPath(), permission.getApiPath()) && Objects.equals(dto.getMethod(), permission.getMethod())) {
            checkExistsByApiPathAndMethod(dto.getApiPath(), dto.getMethod());
        }
        permissionMapper.updatePermissionFromDto(dto, permission);
        Permission updatedPermission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponseDto(updatedPermission);
    }

    public void delete(Long id) {
        Permission permission = getPermissionByIdOrThrow(id);
        permissionRepository.delete(permission);
    }

    public Permission getPermissionByIdOrThrow(Long id) {
        return permissionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Permission not found")
        );
    }

    public void checkExistsByApiPathAndMethod(String apiPath, String method) {
        Boolean exists = permissionRepository.existsByApiPathAndMethod(apiPath, method);
        if (exists) {
            throw new IllegalArgumentException("Permission with the same API path and method already exists.");
        }
    }


}
