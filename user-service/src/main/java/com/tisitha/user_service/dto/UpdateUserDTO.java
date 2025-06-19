package com.tisitha.user_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {

    private String email;

    private String password;

    private String passwordRepeat;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String address1;

    private String address2;

    private String contactNo;

    private String currentPassword;
}
