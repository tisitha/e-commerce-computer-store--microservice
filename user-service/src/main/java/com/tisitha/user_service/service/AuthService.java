package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.LoginRequestDTO;
import com.tisitha.user_service.dto.PasswordDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.dto.UpdateUserDTO;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    void register(RegisterRequestDTO registerRequestDTO);

    boolean validateToken(String token) ;

    void updateUser(UUID id, UpdateUserDTO updateUserDTO);

    void deleteUser(UUID id, PasswordDTO pass);

}
