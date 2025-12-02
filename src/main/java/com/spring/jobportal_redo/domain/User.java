package com.spring.jobportal_redo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email can not be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Password can not be empty")
    private String password;


}
