package com.example.validationdocument.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendValidationEmail(String toEmail, String sujet, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject(sujet);
        mail.setText(message);
        mailSender.send(mail);
    }
}
