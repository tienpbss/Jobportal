package com.spring.jobportal_redo.domain.dto.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreateDto {
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "API Path can not be blank")
    private String apiPath;
    @NotBlank(message = "Method can not be blank")
    private String method;
    @NotBlank(message = "Module can not be blank")
    private String module;
}
