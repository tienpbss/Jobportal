package com.spring.jobportal_redo.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.util.constant.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

public class UserResponDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private Integer age;
    private Gender gender;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
}
