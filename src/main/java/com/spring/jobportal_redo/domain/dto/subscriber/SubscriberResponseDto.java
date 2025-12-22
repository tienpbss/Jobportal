package com.spring.jobportal_redo.domain.dto.subscriber;

import com.spring.jobportal_redo.domain.dto.skill.SkillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberResponseDto {
    private Long id;
    private String email;
    Set<SkillResponseDto> skills = new HashSet<>();
}
