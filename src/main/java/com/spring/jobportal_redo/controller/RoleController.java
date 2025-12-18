package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Role;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.role.RoleCreateDto;
import com.spring.jobportal_redo.domain.dto.role.RoleResponseDto;
import com.spring.jobportal_redo.domain.dto.role.RoleUpdateDto;
import com.spring.jobportal_redo.service.RoleService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleResponseDto create(@RequestBody @Valid RoleCreateDto dto){
        return roleService.create(dto);
    }

    @GetMapping("/{id}")
    public RoleResponseDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<Role> spec,
            Pageable pageable
            ) {
        return roleService.getAll(spec, pageable);
    }

    @PutMapping
    public RoleResponseDto update(@RequestBody @Valid RoleUpdateDto dto) {
        return roleService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

}
