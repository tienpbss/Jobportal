package com.spring.jobportal_redo.domain;

import com.spring.jobportal_redo.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"apiPath", "method"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String apiPath;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private String module;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();


    private Instant createdAt;
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
