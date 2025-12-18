package com.spring.jobportal_redo.domain.dto.permission;

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
public class PermissionUpdateDto {
    @NotNull(message = "Id can not be null")
    private Long id;
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "API Path can not be blank")
    private String apiPath;
    @NotBlank(message = "Method can not be blank")
    private String method;
    @NotBlank(message = "Module can not be blank")
    private String module;
}
