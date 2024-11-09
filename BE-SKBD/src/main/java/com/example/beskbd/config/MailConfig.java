package com.example.beskbd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Correct SMTP host
        mailSender.setPort(587); // Port for TLS

        mailSender.setUsername("dangqwe321@gmail.com"); // Your Gmail address
        mailSender.setPassword("axlxwjjjaqzmamrg"); // Your Gmail password or App Password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use TLS
        props.put("mail.debug", "true");

        return mailSender;
    }
}