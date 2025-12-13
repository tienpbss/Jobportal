package com.spring.jobportal_redo.domain.dto.job;

import com.spring.jobportal_redo.util.constant.JobLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobUpdateDto {
    @NotNull(message = "Missing id")
    private Long id;
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Location can not be blank")
    private String location;
    @NotBlank(message = "Salary can not be blank")
    private Double salary;
    @NotBlank(message = "Quantity can not be blank")
    private Integer quantity;
    @NotBlank(message = "Level can not be blank")
    private JobLevel level;
    @NotBlank(message = "Description can not be blank")
    private String description;
    private Instant startDate;
    private Instant endDate;
    @NotBlank(message = "Active can not be blank")
    private Boolean active;
    private Long companyId;
    Set<Long> skillIds = new HashSet<>();
}
