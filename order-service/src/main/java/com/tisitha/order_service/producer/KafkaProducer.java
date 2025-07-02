package com.tisitha.order_service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String mailBody){
        Message<String> mailBodyMessage = MessageBuilder
                .withPayload(mailBody)
                .setHeader(KafkaHeaders.TOPIC,"notification")
                .build();

        kafkaTemplate.send(mailBodyMessage);
    }

}
