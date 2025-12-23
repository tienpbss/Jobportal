package com.spring.jobportal_redo.domain;

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
public class JobInEmail {
    private String jobName;
    private String companyName;
    private Double salary;
    Set<String> skillNames = new HashSet<>();

}
