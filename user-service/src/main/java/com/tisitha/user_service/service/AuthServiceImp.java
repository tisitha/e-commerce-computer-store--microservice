package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.LoginRequestDTO;
import com.tisitha.user_service.dto.PasswordDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.dto.UpdateUserDTO;
import com.tisitha.user_service.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImp implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImp(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){

        return userService
                .findByEmail(loginRequestDTO.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole().toString()));
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        userService.register(registerRequestDTO);
    }

    @Override
    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public void updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        userService.updateUser(id,updateUserDTO);
    }

    @Override
    public void deleteUser(UUID id, PasswordDTO pass) {
        userService.deleteUser(id,pass);
    }

}
