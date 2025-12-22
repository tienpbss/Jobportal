package com.spring.jobportal_redo.domain.dto.subscriber;

import jakarta.validation.constraints.NotEmpty;
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
public class SubscriberDto {
    @NotEmpty(message = "Skill subscribe could not be empty")
    Set<Long> skillIds = new HashSet<>();
}
