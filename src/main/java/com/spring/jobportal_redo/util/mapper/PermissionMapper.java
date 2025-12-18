package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Permission;
import com.spring.jobportal_redo.domain.dto.permission.PermissionCreateDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionResponseDto;
import com.spring.jobportal_redo.domain.dto.permission.PermissionUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreateDto createDto);
    PermissionResponseDto toPermissionResponseDto(Permission permission);
    List<PermissionResponseDto> toPermissionResponseDtoList(List<Permission> permissions);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionFromDto(PermissionUpdateDto dto, @MappingTarget Permission permission);
}
