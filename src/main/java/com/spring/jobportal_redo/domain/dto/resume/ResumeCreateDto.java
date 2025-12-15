package com.spring.jobportal_redo.domain.dto.resume;

import com.spring.jobportal_redo.util.constant.ResumeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeCreateDto {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotBlank(message = "Url can not be blank")
    private String url;
    @NotNull(message = "UserId can not be null")
    private Long userId;
    @NotNull(message = "JobId can not be null")
    private Long jobId;
}
