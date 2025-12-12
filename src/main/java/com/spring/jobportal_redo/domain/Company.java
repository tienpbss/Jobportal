package com.spring.jobportal_redo.domain;

import com.spring.jobportal_redo.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    private String name;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String address;
    private String logo;
    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<User> users = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        Optional<String> createdByName = SecurityUtil.getPrincipalCurrentUserLogin();
        this.createdBy = createdByName.orElse("");
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
        Optional<String> updatedByName = SecurityUtil.getPrincipalCurrentUserLogin();
        this.updatedBy = updatedByName.orElse("");
    }
}
