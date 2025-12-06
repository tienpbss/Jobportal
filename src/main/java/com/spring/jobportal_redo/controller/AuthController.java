package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.dto.JwtResponseDto;
import com.spring.jobportal_redo.domain.dto.LoginDto;
import com.spring.jobportal_redo.service.UserService;
import com.spring.jobportal_redo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setToken(securityUtil.createToken(authentication));
        return ResponseEntity.ok(jwtResponseDto);
    }
}
