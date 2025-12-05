package com.spring.jobportal_redo.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaPaging {
    private int page;
    private int size;
    private int pages;
    private long total;
}
