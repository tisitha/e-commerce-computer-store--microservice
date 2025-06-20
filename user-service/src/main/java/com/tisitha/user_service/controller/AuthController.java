package com.tisitha.user_service.controller;

import com.tisitha.user_service.dto.ChangePassword;
import com.tisitha.user_service.dto.LoginRequestDTO;
import com.tisitha.user_service.dto.LoginResponseDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.service.AuthService;
import com.tisitha.user_service.service.ForgotPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final ForgotPasswordService forgotPasswordService;

    public AuthController(AuthService authService, ForgotPasswordService forgotPasswordService) {
        this.authService = authService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO){

            authService.register(registerRequestDTO);
            return new ResponseEntity<>("successfully account created",HttpStatus.CREATED);

    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateToken(authHeader.substring(7))
                ?ResponseEntity.ok().build()
                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/user-update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody RegisterRequestDTO registerRequestDTO) {
        try{
            authService.updateUser(id,registerRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/user-delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try{
            authService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verifymail/{email}")
    public ResponseEntity<Void> verifyEmail(@PathVariable String email){
        try{
            forgotPasswordService.verifyEmail(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
    @PostMapping("/varifyotp/{otp}/{email}")
    public ResponseEntity<Void> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
        try{
            forgotPasswordService.verifyOtp(otp,email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/changepassword/{otp}/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable Integer otp, @PathVariable String email){
        try{
            forgotPasswordService.changePasswordHandler(changePassword,otp,email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
