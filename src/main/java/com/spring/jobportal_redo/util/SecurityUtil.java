package com.spring.jobportal_redo.util;

import com.nimbusds.jose.util.Base64;
import com.spring.jobportal_redo.domain.dto.JwtResponseDto;
import com.spring.jobportal_redo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityUtil {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    @Value("${jwt.expiration}")
    private String jwtExpiration;

    @Value("${jwt.key}")
    private String jwtKey;

    public String createToken(JwtResponseDto.UserLogin userLogin) {
        Instant now = Instant.now();
        Instant validity = now.plusSeconds(Long.parseLong(jwtExpiration));

        List<String> permissions = Arrays.asList("ROLE_USER_CREATE", "ROLE_USER_UPDATE", "ROLE_USER_DELETE");
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(userLogin.getEmail())
                .claim("user", userLogin)
                .claim("permissions", permissions)
                .build();
        JwsHeader jwtHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtHeader, jwtClaimsSet)).getTokenValue();
    }

    public String createRefreshToken(JwtResponseDto.UserLogin userLogin) {
        Instant now = Instant.now();
        Instant validity = now.plusSeconds(Long.parseLong(jwtExpiration));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(userLogin.getEmail())
                .claim("user", userLogin)
                .build();
        JwsHeader jwtHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtHeader, jwtClaimsSet)).getTokenValue();
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */

    public static Optional<String> getPrincipalCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public Jwt getJwtObjectIfValid(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return jwtDecoder.decode(token);
    }
}
