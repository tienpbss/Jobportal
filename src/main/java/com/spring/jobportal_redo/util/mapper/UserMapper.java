package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.JwtResponseDto;
import com.spring.jobportal_redo.domain.dto.user.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateDto dto);
    User toUser(UserRegisterDto dto);

    UserResponseDto toResponse(User user);
    List<UserResponseDto> toResponseList(List<User> users);

    @Mapping(target = "id", ignore = true)           // id never changes
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserEditInfoDto dto, @MappingTarget User user);
}
