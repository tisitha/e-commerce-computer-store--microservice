package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.LoginRequestDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    void register(RegisterRequestDTO registerRequestDTO);

    boolean validateToken(String token) ;

    void updateUser(UUID id, RegisterRequestDTO registerRequestDTO);

    void deleteUser(UUID id);

}
