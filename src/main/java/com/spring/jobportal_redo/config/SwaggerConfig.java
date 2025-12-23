package com.spring.jobportal_redo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jobportal REST API")
                        .description("Some custom description of the API.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Tien Phung")
                                .url("https://www.example.com")
                                .email("no-send@company.com"))
                        .license(new License()
                                .name("Spring 3.5.9")
                                .url("#")));
    }
}
