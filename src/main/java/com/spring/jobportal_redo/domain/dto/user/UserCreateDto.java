package com.spring.jobportal_redo.domain.dto.user;

import com.spring.jobportal_redo.domain.dto.RefDto;
import com.spring.jobportal_redo.util.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String email;
    private String name;
    private String password;
    private Integer age;
    private Gender gender;
    private String address;
    private RefDto company;

}
