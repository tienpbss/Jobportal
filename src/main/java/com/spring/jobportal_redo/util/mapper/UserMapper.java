package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.JwtResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserCreateDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.domain.dto.user.UserUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "company", ignore = true)
    User toUser(UserCreateDto dto);
    UserResponseDto toResponse(User user);
    List<UserResponseDto> toResponseList(List<User> users);

    JwtResponseDto.UserLogin toUserLogin(User user);
    @Mapping(target = "id", ignore = true)           // id never changes
    @Mapping(target = "company", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);
}
