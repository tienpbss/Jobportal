package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Permission;
import com.spring.jobportal_redo.domain.Role;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.role.RoleCreateDto;
import com.spring.jobportal_redo.domain.dto.role.RoleResponseDto;
import com.spring.jobportal_redo.domain.dto.role.RoleUpdateDto;
import com.spring.jobportal_redo.repository.RoleRepository;
import com.spring.jobportal_redo.util.mapper.RoleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    public RoleResponseDto create(@Valid RoleCreateDto dto) {
        checkRoleNameExists(dto.getName());
        Role role = roleMapper.toRole(dto);
        assignPermissionToRoleFromIds(dto.getPermissionIds(), role);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toRoleResponseDto(savedRole);
    }

    public RoleResponseDto getById(Long id) {
        Role role = getRoleByIdOrThrow(id);
        return roleMapper.toRoleResponseDto(role);
    }

    public PagingReturnDto getAll(Specification<Role> spec, Pageable pageable) {
        Page<Role> page = roleRepository.findAll(spec, pageable);
        MetaPaging mt = new MetaPaging(page.getNumber() +1, page.getSize(), page.getTotalPages(), page.getTotalElements());
        List<RoleResponseDto> permissionResponseDtoList = roleMapper.toRoleResponseDtoList(page.getContent());
        return new PagingReturnDto(mt, permissionResponseDtoList);
    }

    public RoleResponseDto update(RoleUpdateDto dto) {
        Role role = getRoleByIdOrThrow(dto.getId());
        if (!dto.getName().equals(role.getName())) {
            checkRoleNameExists(dto.getName());
        }
        roleMapper.updateRoleFromDto(dto, role);
        role.removeAllPermissions();
        assignPermissionToRoleFromIds(dto.getPermissionIds(), role);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toRoleResponseDto(updatedRole);
    }

    public void delete(Long id) {
        Role role = getRoleByIdOrThrow(id);
        roleRepository.delete(role);
    }

    public void assignPermissionToRoleFromIds(Set<Long> permissionIds, Role role) {
        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<Permission>());
        }
        for (Long permissionId : permissionIds) {
            Permission permission = permissionService.getPermissionByIdOrThrow(permissionId);
            role.addPermission(permission);
        }
    }

    public Role getRoleByIdOrThrow(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Role not found with id: " + id));
    }

    public void checkRoleNameExists(String name) {
        if (roleRepository.existsByName(name)) {
            throw new IllegalArgumentException("Role with name " + name + " already exists");
        }
    }
}
