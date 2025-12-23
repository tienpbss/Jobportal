package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.*;
import com.spring.jobportal_redo.repository.JobRepository;
import com.spring.jobportal_redo.repository.SkillRepository;
import com.spring.jobportal_redo.repository.SubscriberRepository;
import com.spring.jobportal_redo.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private final SubscriberRepository subscriberRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Async
    @Transactional
    @Scheduled(cron = "0 0 0 * * 6")
    public void sendEmail() {
        String subject = "Viec lam IT";
        boolean isMultipart = false;
        boolean isHtml = true;
        String templateName = "job";

        List<Subscriber> subscribers = subscriberRepository.findAll();
        for (Subscriber subscriber : subscribers) {
            List<JobInEmail> jobInEmails = getJobForEmail(subscriber);
            String to = subscriber.getEmail();
            User user = userRepository.findByEmail(to).orElse(null);

            Context context = new Context();
            context.setVariable("name", user!=null ? user.getName() : "");
            context.setVariable("jobInEmails", jobInEmails);
            String content = templateEngine.process(templateName, context);
//            to = "reinadear@airsworld.net";
            logger.debug("Sending email to {}", to);
            sendEmailSync(to, subject, content, isMultipart, isHtml);
        }

    }

    private List<JobInEmail> getJobForEmail (Subscriber subscriber) {
        Set<Skill> skills = subscriber.getSkills();
        List<Job> jobs = jobRepository.findBySkillsIn(skills);
        List<JobInEmail> jobInEmails = new ArrayList<>();
        for (Job job : jobs) {
            JobInEmail jobInEmail = new JobInEmail();
            jobInEmail.setJobName(job.getName());
            jobInEmail.setSalary(job.getSalary());
            jobInEmail.setCompanyName(job.getCompany().getName());
            jobInEmail.setSkillNames(
                    job.getSkills().stream().map(Skill::getName).collect(Collectors.toSet())
            );
            jobInEmails.add(jobInEmail);
        }
        return jobInEmails;
    }

    private void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            String mailFrom = "hidden@gmail.com";

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(mailFrom);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            logger.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            logger.warn("Email could not be sent to user '{}'", to, e);
        }
    }

}
