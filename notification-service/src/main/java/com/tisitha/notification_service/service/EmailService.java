package com.tisitha.notification_service.service;

import com.tisitha.notification_service.payload.MailBody;

public interface EmailService {

    void sendSimpleMessage(MailBody mailbody);
}
