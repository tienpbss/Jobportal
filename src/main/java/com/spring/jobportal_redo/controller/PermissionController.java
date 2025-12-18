package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Permission;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionCreateDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionResponseDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionUpdateDto;
import com.spring.jobportal_redo.service.PermissionService;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public PermissionResponseDto create(@RequestBody @Valid PermissionCreateDto dto) {
        return permissionService.create(dto);
    }

    @GetMapping("/{id}")
    public PermissionResponseDto getById(@PathVariable Long id) {
        return permissionService.getById(id);

    }

    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<Permission> specification,
            Pageable pageable
            ) {
        return permissionService.getAll(specification, pageable);
    }

    @PutMapping
    public PermissionResponseDto update(@RequestBody @Valid PermissionUpdateDto dto) {
        return permissionService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ApiMessage(message = "Permission deleted successfully")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }

}
