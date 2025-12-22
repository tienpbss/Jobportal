package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.domain.dto.MetaPaging;
import com.spring.jobportal_redo.domain.dto.PagingReturnDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillCreateDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillResponseDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillUpdateDto;
import com.spring.jobportal_redo.repository.SkillRepository;
import com.spring.jobportal_redo.util.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    public SkillResponseDto create(SkillCreateDto dto) {
        checkNameExists(dto.getName());
        Skill skill = skillMapper.toSkill(dto);
        Skill skill1 = skillRepository.save(skill);
        return skillMapper.toSkillResponseDto(skill1);
    }

    public SkillResponseDto getById(Long id) {
        Skill skill = getByIdOrThrow(id);
        return skillMapper.toSkillResponseDto(skill);
    }

    public PagingReturnDto getAll(Specification<Skill> specification, Pageable pageable) {
        Page<Skill> page = skillRepository.findAll(specification, pageable);
        MetaPaging mt = MetaPaging.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
        List<SkillResponseDto> result = skillMapper.toSkillResponseDtoList(page.getContent());
        return PagingReturnDto.builder()
                .meta(mt)
                .result(result)
                .build();
    }

    public SkillResponseDto update(SkillUpdateDto dto) {
        Skill skill = getByIdOrThrow(dto.getId());
        if (!dto.getName().equals(skill.getName())) {
            checkNameExists(dto.getName());
        }

        skillMapper.updateSkillFromDto(dto, skill);
        Skill updatedSkill = skillRepository.save(skill);
        return skillMapper.toSkillResponseDto(updatedSkill);
    }

    public void delete(Long id) {
        Skill skill = getByIdOrThrow(id);
        skill.clearJobs();
        skill.clearSubscribers();
        skillRepository.delete(skill);
    }
    public Skill getByIdOrThrow(Long id) {
        return skillRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Skill ID " + id + " not found.")
                );
    }

    public void checkNameExists(String name) {
        if (skillRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Skill already exists!");
        }
    }

}
