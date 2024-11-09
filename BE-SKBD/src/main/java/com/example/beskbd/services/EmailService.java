package com.example.beskbd.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender mailSender; // Ensure this is injected

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("dangqwe321@gmail.com");
        // Specify the sender’s email

        mailSender.send(message);
    }
}