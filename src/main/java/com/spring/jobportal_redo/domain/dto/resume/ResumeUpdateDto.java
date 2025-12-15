package com.spring.jobportal_redo.domain.dto.resume;

import com.spring.jobportal_redo.util.constant.ResumeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUpdateDto {
    @NotNull(message = "Id can not be null")
    private Long id;
    @NotNull(message = "Status can not be null")
    private ResumeStatus status;
}
