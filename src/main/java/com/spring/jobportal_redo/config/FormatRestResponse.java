package com.spring.jobportal_redo.config;

import com.spring.jobportal_redo.domain.dto.ApiResponse;
import com.spring.jobportal_redo.util.annotation.ApiMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // Apply to all responses
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType contentType,
            Class converterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        // Do not wrap if already RestResponse
        if (body instanceof ApiResponse || body instanceof String) {
            return body;
        }

        // Do not wrap Swagger / spring docs / error html
        String path = request.getURI().getPath();
        if (path.contains("swagger") || path.contains("api-docs")) {
            return body;
        }

        ApiMessage apiMessage = returnType.getMethodAnnotation(ApiMessage.class);
        String message = (apiMessage == null) ? "Call api successful" : apiMessage.message();

        // Wrap in success response
        return new ApiResponse<>(HttpStatus.OK, message, body);
    }
}
