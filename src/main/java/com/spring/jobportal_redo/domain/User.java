package com.spring.jobportal_redo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.constant.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email can not be empty")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Name can not be empty")
    private String name;
    @Column(nullable = false)
    @NotEmpty(message = "Password can not be empty")
    private String password;
    private Integer age;
    private Gender gender;
    private String address;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        createdBy = SecurityUtil.getPrincipalCurrentUserLogin().orElse(null);
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
        updatedBy = SecurityUtil.getPrincipalCurrentUserLogin().orElse(null);
    }

}
