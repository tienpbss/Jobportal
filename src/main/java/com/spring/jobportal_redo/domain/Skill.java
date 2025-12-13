package com.spring.jobportal_redo.domain;

import com.spring.jobportal_redo.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Job> jobs = new HashSet<>();

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
