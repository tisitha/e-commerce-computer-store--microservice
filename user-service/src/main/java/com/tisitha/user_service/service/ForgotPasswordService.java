package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.ChangePassword;

public interface ForgotPasswordService {

    void verifyEmail(String email);

    void verifyOtp(Integer otp,String email);

    void changePasswordHandler(ChangePassword changePassword, Integer otp, String email);
}
