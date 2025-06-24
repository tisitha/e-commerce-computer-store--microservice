package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.*;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO);

    void register(RegisterRequestDTO registerRequestDTO);

    boolean validateToken(String token) ;

    boolean validateAdminToken(String token);

    boolean validateTokenSubject(String token, UUID customerId);

    void updateUser(UUID id, UpdateUserDTO updateUserDTO);

    void deleteUser(UUID id, PasswordDTO pass);

}
