package com.spring.jobportal_redo.domain.dto.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.util.constant.JobLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDto {
    private Long id;
    private String name;
    private String location;
    private Double salary;
    private Integer quantity;
    private JobLevel level;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant endDate;
    private Boolean active;
    private CompanyDto company;
    Set<SkillDto> skills = new HashSet<>();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyDto {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillDto {
        private Long id;
        private String name;
    }
}
