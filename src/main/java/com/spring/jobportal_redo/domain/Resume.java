package com.spring.jobportal_redo.domain;

import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.constant.ResumeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResumeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        status = ResumeStatus.PENDING;
        createdAt = Instant.now();
        createdBy = SecurityUtil.getPrincipalCurrentUserLogin().orElse(null);
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
        updatedBy = SecurityUtil.getPrincipalCurrentUserLogin().orElse(null);
    }

    public void unAssignUser() {
        if (this.user != null) {
            this.user.getResumes().remove(this);
            this.user = null;
        }
    }

    public void unAssignJob() {
        if (this.job != null) {
            this.job.getResumes().remove(this);
            this.job = null;
        }
    }
}
