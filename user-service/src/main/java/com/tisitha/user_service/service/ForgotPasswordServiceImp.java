package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.ChangePassword;
import com.tisitha.user_service.dto.MailBody;
import com.tisitha.user_service.exception.OtpExpiredException;
import com.tisitha.user_service.exception.OtpInvalidException;
import com.tisitha.user_service.exception.PasswordsNotMatchingException;
import com.tisitha.user_service.exception.UserNotFoundException;
import com.tisitha.user_service.model.ForgotPassword;
import com.tisitha.user_service.model.User;
import com.tisitha.user_service.repository.ForgotPasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgotPasswordServiceImp implements ForgotPasswordService{

    private final UserService userService;

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ForgotPasswordServiceImp(UserService userService, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.userService = userService;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    public void verifyEmail(String email){
        User user = userService.findByEmail(email).orElseThrow(()->new UserNotFoundException("User(email:"+email+") is invalid"));
        forgotPasswordRepository.deleteByUserId(user.getId());
        int otp = otpGenerator();
        MailBody mailbody = MailBody.builder()
                .to(email)
                .text("This is the OPT for your forgot password request "+otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+60*1000*5))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailbody);

        forgotPasswordRepository.save(fp);

    }

    @Override
    public void verifyOtp(Integer otp,String email){
        User user = userService.findByEmail(email).orElseThrow(()->new UserNotFoundException("User(email:"+email+") is invalid"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new OtpInvalidException("OTP is invalid"));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpId());
            throw new OtpExpiredException("Otp expired");
        }
    }

    @Override
    public void changePasswordHandler(ChangePassword changePassword, Integer otp, String email){
        User user = userService.findByEmail(email).orElseThrow(()->new UserNotFoundException("User(email:"+email+") is invalid"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new OtpInvalidException("OTP is invalid"));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpId());
            throw new OtpExpiredException("Otp expired");
        }

        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
            throw new PasswordsNotMatchingException("Passwords not matching");
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userService.updatePassword(email,encodedPassword);

        forgotPasswordRepository.deleteById(fp.getFpId());
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100000,999999);
    }
}