package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Job;
import com.spring.jobportal_redo.domain.dto.job.JobCreateDto;
import com.spring.jobportal_redo.domain.dto.job.JobResponseDto;
import com.spring.jobportal_redo.domain.dto.job.JobUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "skills", ignore = true)
    Job toJob(JobCreateDto createDto);
    JobResponseDto toResponseDto(Job job);
    List<JobResponseDto> toResponseDto(List<Job> jobs);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "skills", ignore = true)
    void updateJobFromDto(JobUpdateDto jobUpdateDto, @MappingTarget Job job);
}
