package com.spring.jobportal_redo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
// Source - https://stackoverflow.com/a
// Posted by Joker
// Retrieved 2025-11-27, License - CC BY-SA 4.0

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class JobportalRedoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobportalRedoApplication.class, args);
	}

}
