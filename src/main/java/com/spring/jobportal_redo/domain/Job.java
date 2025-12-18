package com.spring.jobportal_redo.domain;

import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.constant.JobLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Double salary;
    @Column(nullable = false)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobLevel level;
    @Column(nullable = false)
    private String description;
    private Instant startDate;
    private Instant endDate;
    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "job_skill",  // Join table name
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resume> resumes = new ArrayList<>();

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

    public void addSkill(Skill skill) {
        if (skill == null) {return;}
        this.skills.add(skill);
        skill.getJobs().add(this);
    }

    public void addSkills(Set<Skill> skills) {
        this.skills.addAll(skills);
        skills.forEach(this::addSkill);
    }

    public void clearSkills() {
        for (Skill skill : new HashSet<>(skills)) {
            skill.getJobs().remove(this);
        }
        this.skills.clear();
    }

    public void addResume(Resume resume) {
        resumes.add(resume);
        resume.setJob(this);
    }

    public void removeAllRelations(Resume resume) {
        for (Skill skill : new HashSet<>(skills)) {
            skill.getJobs().remove(this);
        }
        this.skills.clear();
    }
}
