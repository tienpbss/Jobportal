package com.spring.jobportal_redo.domain.dto.skill;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillUpdateDto {
    private Long id;
    @NotBlank(message = "Name skill can not be blank")
    private String name;
}
