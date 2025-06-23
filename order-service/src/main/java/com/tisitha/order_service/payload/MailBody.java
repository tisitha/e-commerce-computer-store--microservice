package com.tisitha.order_service.payload;

import lombok.Builder;

@Builder
public record MailBody(String subject, String text) {
}
