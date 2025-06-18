package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.ChangePassword;
import com.tisitha.user_service.dto.MailBody;
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
        User user = userService.findByEmail(email).orElseThrow(()->new RuntimeException("Email not found"));
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
        User user = userService.findByEmail(email).orElseThrow(()->new RuntimeException("cannot find email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new RuntimeException("Invalid OTP and email"+email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpId());
            throw new RuntimeException("OTP expired");
        }
    }

    @Override
    public void changePasswordHandler(ChangePassword changePassword, Integer otp, String email){
        User user = userService.findByEmail(email).orElseThrow(()->new RuntimeException("email not found"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(()->new RuntimeException("Invalid OTP and email"+email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpId());
            throw new RuntimeException("Otp expired");
        }

        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
            throw new RuntimeException("Please enter the password again");
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userService.updatePassword(email,encodedPassword);

        forgotPasswordRepository.deleteById(fp.getFpId());

        throw new RuntimeException("Password has been changed");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100000,999999);
    }
}