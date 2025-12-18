package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Role;
import com.spring.jobportal_redo.domain.dto.role.RoleCreateDto;
import com.spring.jobportal_redo.domain.dto.role.RoleResponseDto;
import com.spring.jobportal_redo.domain.dto.role.RoleUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreateDto dto);
    RoleResponseDto toRoleResponseDto(Role role);
    List<RoleResponseDto> toRoleResponseDtoList(List<Role> roles);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateRoleFromDto(RoleUpdateDto dto, @MappingTarget Role role);
}
