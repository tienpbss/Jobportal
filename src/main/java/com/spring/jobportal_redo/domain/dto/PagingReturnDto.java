package com.spring.jobportal_redo.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingReturnDto {
    MetaPaging meta;
    Object result;
}
