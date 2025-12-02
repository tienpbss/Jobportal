package com.spring.jobportal_redo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.util.SecurityUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name of company can not be blank")
    private String name;

    private String description;

    private String address;

    private String logo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        Optional<String> createdByName = SecurityUtil.getCurrentUserLogin();
        this.createdBy = createdByName.orElse("");
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
        Optional<String> updatedByName = SecurityUtil.getCurrentUserLogin();
        this.updatedBy = updatedByName.orElse("");
    }
}
