package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.*;
import com.tisitha.user_service.exception.PasswordIncorrectException;
import com.tisitha.user_service.exception.ValidateFailedException;
import com.tisitha.user_service.model.User;
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
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO){

        Optional<User> userOptional = userService.findByEmail(loginRequestDTO.getEmail());
        if(userOptional.isEmpty()){
            throw new PasswordIncorrectException("Email or password incorrect");
        }
        String uid = userOptional.get().getId().toString();

        Optional<String> tokenOptional = userOptional
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getId(),u.getRole().toString()));

        if(tokenOptional.isEmpty()){
            throw new PasswordIncorrectException("Email or password incorrect");
        }

        return new LoginResponseDTO(tokenOptional.get(),userOptional.get().getEmail(),uid,userOptional.get().getRole().toString());
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        userService.register(registerRequestDTO);
    }

    @Override
    public UserIdResponse validateToken(String token) {
        try{
            return new UserIdResponse(jwtUtil.validateToken(token));
        } catch (JwtException e) {
            throw new ValidateFailedException("Token is invalid");
        }
    }

    @Override
    public UserIdResponse validateAdminToken(String token) {
        try{
            return new UserIdResponse(jwtUtil.validateAdminToken(token));
        } catch (JwtException e) {
            throw new ValidateFailedException("Token is invalid( not an admin )");
        }
    }

    @Override
    public boolean validateTokenSubject(String token, UUID customerId) {
        try{
            String expectedEmail = userService.getEmailById(customerId);
            jwtUtil.validateTokenSubject(token,expectedEmail);
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
