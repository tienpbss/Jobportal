package com.spring.jobportal_redo.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.util.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponDto {
    private Long id;
    private String email;
    private String name;
    private Integer age;
    private Gender gender;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
}
