package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillCreateDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillResponseDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillUpdateDto;
import com.spring.jobportal_redo.service.SkillService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    public SkillResponseDto create(@RequestBody @Valid SkillCreateDto dto) {
        return this.skillService.create(dto);
    }

    @GetMapping("/{id}")
    public SkillResponseDto getById(@PathVariable Long id) {
        return skillService.getById(id);
    }

    @GetMapping
    public PagingReturnDto getAll(
            @Filter Specification<Skill> specification,
            Pageable pageable
    ) {
        return skillService.getAll(specification, pageable);
    }

    @PutMapping
    public SkillResponseDto update(@RequestBody @Valid SkillUpdateDto dto) {
        return skillService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        skillService.delete(id);
    }

}
