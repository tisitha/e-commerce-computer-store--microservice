package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.PasswordDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.dto.UpdateUserDTO;
import com.tisitha.user_service.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findByEmail(String email);

    String getEmailById(UUID id);

    void register(RegisterRequestDTO registerRequestDTO);

    void updatePassword(String email, String password);

    void updateUser(UUID id, UpdateUserDTO updateUserDTO);

    void deleteUser(UUID id, PasswordDTO pass);
}