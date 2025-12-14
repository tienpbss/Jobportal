package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Job;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.job.JobCreateDto;
import com.spring.jobportal_redo.domain.dto.job.JobResponseDto;
import com.spring.jobportal_redo.domain.dto.job.JobUpdateDto;
import com.spring.jobportal_redo.service.JobService;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public JobResponseDto create(@RequestBody @Valid JobCreateDto createDto) {
        return jobService.create(createDto);
    }

    @GetMapping("/{id}")
    public JobResponseDto getById(@PathVariable Long id) {
        return jobService.getById(id);
    }

    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<Job> specification,
            Pageable pageable
    ) {
        return jobService.getAll(specification, pageable);
    }

    @PutMapping
    public JobResponseDto update(@RequestBody @Valid JobUpdateDto updateDto) {
        return jobService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    @ApiMessage(message = "Deleted job successfully")
    public void delete(@PathVariable Long id) {
        jobService.delete(id);
    }


}
