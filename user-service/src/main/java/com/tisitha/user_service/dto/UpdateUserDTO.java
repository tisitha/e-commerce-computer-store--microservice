package com.tisitha.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    private String password;

    private String passwordRepeat;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    private String address1;

    @NotBlank(message = "Address line 2 is required")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    private String address2;

    @NotBlank(message = "Contact No is required")
    @Size(max = 20, message = "Contact number must not exceed 20 characters")
    @Pattern(regexp = "^(?=(?:[+\\-\\d]*\\d){9,})[+\\-\\d]+$",message = "Contact number must be a valid phone number")
    private String contactNo;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String currentPassword;
}
