package com.spring.jobportal_redo.domain.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RoleUpdateDto {
    @NotNull(message = "Role ID must not be null")
    private Long id;
    @NotBlank(message = "Role name must not be blank")
    private String name;
    private String description;
    @NotNull(message = "Active status must not be null")
    private Boolean active;
    Set<Long> permissionIds = new HashSet<>();
}
