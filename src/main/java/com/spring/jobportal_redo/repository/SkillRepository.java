package com.spring.jobportal_redo.repository;

import com.spring.jobportal_redo.domain.Skill;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Range
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {
    Boolean existsByNameIgnoreCase(String skillName);
}
