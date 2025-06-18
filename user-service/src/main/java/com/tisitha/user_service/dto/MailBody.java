package com.tisitha.user_service.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
