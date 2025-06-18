package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.MailBody;

public interface EmailService {

    void sendSimpleMessage(MailBody mailbody);
}
