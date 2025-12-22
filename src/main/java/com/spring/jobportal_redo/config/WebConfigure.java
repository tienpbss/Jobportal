package com.spring.jobportal_redo.config;

import com.spring.jobportal_redo.service.PermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Value("${spring.servlet.upload-file.base-path}")
    private String basePath;

    private final PermissionInterceptor permissionInterceptor;

    public WebConfigure(PermissionInterceptor permissionInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(basePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] whiteListEndpoints = {
                "/", "/error", "/api/v1/auth/login", "/api/v1/auth/refresh", "/storage/**"
//                , "/api/v1/jobs/**", "/api/v1/companies/**", "/api/v1/skills/**"
        };

        // Apply the interceptor to specific paths
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/api/**")      // Only intercept paths starting with /api/
                .excludePathPatterns(whiteListEndpoints);
    }
}
