package com.spring.jobportal_redo.domain.dto.user;

import com.spring.jobportal_redo.util.constant.Gender;
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
public class UserUpdateDto {
    @NotNull
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private Integer age;
    private Gender gender;
    private String address;
}
