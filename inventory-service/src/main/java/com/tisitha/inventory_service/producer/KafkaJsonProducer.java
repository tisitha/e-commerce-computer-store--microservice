package com.tisitha.inventory_service.producer;

import com.tisitha.inventory_service.payload.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaJsonProducer {

    private final KafkaTemplate<String, MailBody> kafkaTemplate;

    public void sendJson(MailBody mailBody){
        Message<MailBody> mailBodyMessage = MessageBuilder
                .withPayload(mailBody)
                .setHeader(KafkaHeaders.TOPIC,"notification")
                .build();

        kafkaTemplate.send(mailBodyMessage);
    }

}
