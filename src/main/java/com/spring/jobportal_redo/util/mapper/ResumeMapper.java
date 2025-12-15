package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Resume;
import com.spring.jobportal_redo.domain.dto.resume.ResumeCreateDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeResponseDto;
import com.spring.jobportal_redo.domain.dto.resume.ResumeUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    Resume toResume(ResumeCreateDto dto);
    ResumeResponseDto toResumeResponseDto(Resume resume);
    List<ResumeResponseDto> toResumeResponseDtoList(List<Resume> resumes);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "job", ignore = true)
    void updateResumeFromDto(ResumeUpdateDto dto, @MappingTarget Resume resume);
}
