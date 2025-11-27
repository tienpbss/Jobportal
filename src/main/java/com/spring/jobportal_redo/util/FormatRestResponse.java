package com.spring.jobportal_redo.util;

import com.spring.jobportal_redo.domain.RestResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;
        int statusCode = servletResponse.getServletResponse().getStatus();
        if (statusCode >= 400 ) {
            return body;
        }
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatusCode(statusCode);
        restResponse.setMessage("CALL API SUCCESS");
        restResponse.setData(body);

        return restResponse;
    }
}
