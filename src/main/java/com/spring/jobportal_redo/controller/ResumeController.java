package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Resume;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeCreateDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeResponseDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeUpdateDto;
import com.spring.jobportal_redo.service.ResumeService;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResumeResponseDto create(@RequestBody @Valid ResumeCreateDto dto) {
        return resumeService.create(dto);
    }

    @GetMapping("/{id}")
    public ResumeResponseDto getById(@PathVariable Long id) {
        return resumeService.getById(id);
    }

    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<Resume> specification,
            Pageable pageable
    ) {
        return resumeService.getAll(specification, pageable);
    }

    @PutMapping
    public ResumeResponseDto update(@RequestBody @Valid ResumeUpdateDto dto) {
        return resumeService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ApiMessage(message = "Resume deleted successfully")
    public void delete(@PathVariable Long id) {
        resumeService.delete(id);
    }

    @PostMapping("/by-user")
    public List<ResumeResponseDto> getResumeOfLoginUser() {
        return resumeService.getResumeOfLoginUser();
    }
}
