package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateDto dto);
    UserResponDto toResponse(User user);
    @Mapping(target = "id", ignore = true)           // id never changes
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);
}
