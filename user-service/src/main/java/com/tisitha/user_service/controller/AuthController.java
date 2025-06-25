package com.tisitha.user_service.controller;

import com.tisitha.user_service.dto.*;
import com.tisitha.user_service.service.AuthService;
import com.tisitha.user_service.service.ForgotPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Generate token and user details on login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

        return new ResponseEntity<>(authService.authenticate(loginRequestDTO),HttpStatus.OK);

    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO){

            authService.register(registerRequestDTO);
            return new ResponseEntity<>("successfully account created",HttpStatus.CREATED);

    }

    @Operation(summary = "Check validity of token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateToken(authHeader.substring(7))
                ?ResponseEntity.ok().build()
                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Check user-role=admin")
    @GetMapping("/validate-admin")
    public ResponseEntity<Void> validateAdminToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateAdminToken(authHeader.substring(7))
                ?ResponseEntity.ok().build()
                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Check token and userID match")
    @GetMapping("/validate-email/{id}")
    public ResponseEntity<Boolean> validateTokenSubject(@RequestHeader("Authorization") String authHeader,@PathVariable UUID id){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateTokenSubject(authHeader.substring(7),id)
                ?ResponseEntity.ok(true)
                :ResponseEntity.ok(false);
    }

    @Operation(summary = "Update user details")
    @PutMapping("/user-update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
        try{
            authService.updateUser(id,updateUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/user-delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, @RequestBody PasswordDTO pass) {
        try{
            authService.deleteUser(id,pass);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Send OTP code to user's email for password reset")
    @PostMapping("/verifymail/{email}")
    public ResponseEntity<Void> verifyEmail(@PathVariable String email){
        try{
            forgotPasswordService.verifyEmail(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Check validity of OTP for password reset")
    @PostMapping("/varifyotp/{otp}/{email}")
    public ResponseEntity<Void> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
        try{
            forgotPasswordService.verifyOtp(otp,email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Add the new password")
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
