package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Company;
import com.spring.jobportal_redo.domain.Job;
import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.job.JobCreateDto;
import com.spring.jobportal_redo.domain.dto.job.JobResponseDto;
import com.spring.jobportal_redo.domain.dto.job.JobUpdateDto;
import com.spring.jobportal_redo.repository.JobRepository;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyService companyService;
    private final SkillService skillService;
    private final JobMapper jobMapper;
    private final UserService userService;

    public JobResponseDto create(JobCreateDto createDto) {
        Company company = getCompanyOfLoginUser();
        HashSet<Skill> skills = createDto
                .getSkillIds().stream()
                .map(skillService::getByIdOrThrow)
                .collect(Collectors.toCollection(HashSet::new));
        Job job = jobMapper.toJob(createDto);
        job.addSkills(skills);
        company.addJob(job);
        Job savedJob = jobRepository.save(job);
        return jobMapper.toResponseDto(savedJob);
    }

    public JobResponseDto getById(Long id) {
        Job job = getJobByIdOrThrow(id);
        return jobMapper.toResponseDto(job);
    }

    public PagingReturnDto getAll(Specification<Job> specification, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(specification, pageable);
        MetaPaging mt = MetaPaging.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
        List<JobResponseDto> jobResponseDtoList = jobMapper.toResponseDto(page.getContent());
        return PagingReturnDto.builder()
                .meta(mt)
                .result(jobResponseDtoList)
                .build();
    }

    public JobResponseDto update(JobUpdateDto updateDto) {
        Job job = getJobByIdOrThrow(updateDto.getId());
        jobMapper.updateJobFromDto(updateDto, job);
        Set<Long> oldSkillIds = new HashSet<>();
        for (Skill skill : job.getSkills()) {
            oldSkillIds.add(skill.getId());
        }
        if (!updateDto.getSkillIds().equals(oldSkillIds)) {
            job.clearSkills();
            HashSet<Skill> updatedSkills = updateDto
                    .getSkillIds().stream()
                    .map(skillService::getByIdOrThrow)
                    .collect(Collectors.toCollection(HashSet::new));
            job.addSkills(updatedSkills);
        }
        Job updatedJob = jobRepository.save(job);
        return jobMapper.toResponseDto(updatedJob);
    }

    public void delete(Long id) {
        jobRepository.delete(getJobByIdOrThrow(id));
    }

    public Job getJobByIdOrThrow(Long id) {
        return jobRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Job Not Found with id: " + id)
        );
    }

    public Company getCompanyOfLoginUser() {
        User user = userService.getUserLogin();
        if (user.getCompany() == null) {
            throw new IllegalArgumentException("User is not associated with any company");
        }
        return user.getCompany();
    }

}
