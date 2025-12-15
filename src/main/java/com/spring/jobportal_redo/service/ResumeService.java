package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Job;
import com.spring.jobportal_redo.domain.Resume;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeCreateDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeResponseDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeUpdateDto;
import com.spring.jobportal_redo.repository.ResumeRepository;
import com.spring.jobportal_redo.util.mapper.ResumeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;

    private final UserService userService;
    private final JobService jobService;

    public ResumeResponseDto create(@Valid ResumeCreateDto dto) {
        User user = userService.getUserByIdOrThrow(dto.getUserId());
        Job job = jobService.getJobByIdOrThrow(dto.getJobId());;
        Resume resume = resumeMapper.toResume(dto);
        user.addResume(resume);
        job.addResume(resume);
        Resume savedResume = resumeRepository.save(resume);
        return resumeMapper.toResumeResponseDto(savedResume);
    }

    public ResumeResponseDto getById(Long id) {
        Resume resume = getResumeByIdOrThrow(id);
        return resumeMapper.toResumeResponseDto(resume);
    }

    public PagingReturnDto getAll(Specification<Resume> specification, Pageable pageable) {
        Page<Resume> resumePage = resumeRepository.findAll(specification, pageable);
        MetaPaging mt = MetaPaging.builder()
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .totalPages(resumePage.getTotalPages())
                .totalElements(resumePage.getTotalElements())
                .build();
        List<ResumeResponseDto> dtoList = resumeMapper.toResumeResponseDtoList(resumePage.getContent());
        return new PagingReturnDto(mt, dtoList);
    }

    public ResumeResponseDto update(@Valid ResumeUpdateDto dto) {
        Resume existingResume = getResumeByIdOrThrow(dto.getId());
        resumeMapper.updateResumeFromDto(dto, existingResume);
        Resume updatedResume = resumeRepository.save(existingResume);
        return resumeMapper.toResumeResponseDto(updatedResume);
    }
    public void delete(Long id) {
        Resume existingResume = getResumeByIdOrThrow(id);
        existingResume.unAssignUser();
        existingResume.unAssignJob();
        resumeRepository.delete(existingResume);
    }

    public Resume getResumeByIdOrThrow(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found with id: " + id));
    }
}
