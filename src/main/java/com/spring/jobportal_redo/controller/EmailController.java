package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public String sendEmail() {
        emailService.sendEmail();
        return "Send email successful";
    }

}
