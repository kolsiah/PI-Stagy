package com.example.user.service;

import com.example.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendUnlockEmail(User user) {
        try {
            String unlockLink = "http://localhost:8081/auth/unlock?email=" + user.getEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Unlock Your Account");
            message.setText("You have entered the wrong password 4 times. Click the link below to unlock your account:\n" + unlockLink);

            mailSender.send(message);
            logger.info("Unlock email sent successfully to: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send unlock email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send unlock email", e);
        }
    }
}