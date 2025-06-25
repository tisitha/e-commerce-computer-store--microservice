package com.tisitha.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

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

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

}
