package com.tisitha.notification_service.consumer;

import com.tisitha.notification_service.payload.MailBody;
import com.tisitha.notification_service.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final EmailService emailService;

    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "notification",groupId = "store")
    public void consumeJsonMsg(String body){
        emailService.sendSimpleMessage(MailBody.builder()
                .subject("Notification - Computer store")
                .text(body)
                .build());
    }
}
