package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.JwtResponseDto;
import com.spring.jobportal_redo.domain.dto.LoginDto;
import com.spring.jobportal_redo.domain.dto.user.UserResponseDto;
import com.spring.jobportal_redo.service.UserService;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final UserMapper userMapper;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getByEmail(loginDto.getEmail());
        JwtResponseDto.UserLogin userLogin = new JwtResponseDto.UserLogin(
                user.getId(),
                user.getEmail(),
                user.getName()
        );

        String token = securityUtil.createToken(userLogin);


        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setToken(token);
        jwtResponseDto.setUser(userLogin);

        String refreshToken = securityUtil.createRefreshToken(jwtResponseDto.getUser());
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)           // Prevents JavaScript access (recommended for auth)
                .secure(true)             // Only send over HTTPS (set false in dev if needed)
                .path("/")                // Available across entire app
                .maxAge(Long.parseLong(jwtExpiration))     // 24 hours
                .sameSite("Strict")       // or "Lax" or "None" (if Secure=true)
                .build();

        userService.saveRefreshToken(user, refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(jwtResponseDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refresh(
            @CookieValue(
                    name = "refresh_token",
                    required = false,        // Don't throw exception if missing
                    defaultValue = ""        // Provide fallback
            ) String token
    ) {
        Jwt jwt = securityUtil.getJwtObjectIfValid(token);
        if (!userService.checkRefreshTokenExists(token)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String email = jwt.getSubject();
        JwtResponseDto.UserLogin userLogin = userLoginFromEmail(email);

        String access_token = securityUtil.createToken(userLogin);

        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setToken(access_token);
        jwtResponseDto.setUser(userLogin);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @GetMapping("/account")
    public ResponseEntity<UserResponseDto> account() {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    private JwtResponseDto.UserLogin userLoginFromEmail(String email) {
        User user = userService.getByEmail(email);
        return userMapper.toUserLogin(user);
    }


}
