package com.tisitha.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/validate-user/{id}")
    ResponseEntity<Boolean> validateTokenSubject(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id);
}
