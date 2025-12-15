package com.spring.jobportal_redo.domain.dto.resume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jobportal_redo.util.constant.ResumeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDto {
    private Long id;
    private String email;
    private String url;
    private ResumeStatus status;
    private UserDto user;
    private JobDto job;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobDto {
        private Long id;
        private String name;
    }
}
