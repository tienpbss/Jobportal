package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.domain.dto.skill.SkillCreateDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillResponseDto;
import com.spring.jobportal_redo.domain.dto.skill.SkillUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toSkill(SkillCreateDto skillCreateDto);
    SkillResponseDto toSkillResponseDto(Skill skill);
    List<SkillResponseDto> toSkillResponseDtoList(List<Skill> skillList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateSkillFromDto(SkillUpdateDto skillUpdateDto, @MappingTarget Skill skill);
}
