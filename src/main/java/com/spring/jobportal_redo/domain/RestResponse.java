package com.spring.jobportal_redo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestResponse<T> {
    private Integer statusCode;
    private String error;
    private Object message;
    private T data;
}
