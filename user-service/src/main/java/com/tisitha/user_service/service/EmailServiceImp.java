package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.MailBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService{

    private final JavaMailSender javaMailSender;

    @Value("${mail.email}")
    private String email;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailbody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailbody.to());
        message.setFrom(email);
        message.setSubject(mailbody.subject());
        message.setText(mailbody.text());

        javaMailSender.send(message);
    }
}