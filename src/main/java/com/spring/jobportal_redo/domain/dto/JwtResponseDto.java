package com.spring.jobportal_redo.domain.dto;

import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String token;
    private UserResponseDto user;
}
