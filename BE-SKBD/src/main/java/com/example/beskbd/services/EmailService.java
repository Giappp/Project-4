package com.example.beskbd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("dangqwe321@gmail.com"); // Your sender email

        mailSender.send(message);
    }

    public void sendVerificationEmail(String email, String verificationUrl) {
        String subject = "Email Verification";
        String text = "Click the following link to verify your email: " + verificationUrl;
        sendResetLink(email, subject, text);
    }
}