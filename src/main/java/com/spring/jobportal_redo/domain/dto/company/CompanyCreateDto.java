package com.spring.jobportal_redo.domain.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateDto {
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Description can not be blank")
    private String description;
    @NotBlank(message = "Address can not be blank")
    private String address;
    private String logo;
}
