package com.spring.jobportal_redo.domain.dto;

import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String token;
    private UserResponseDto user;
}
