package com.tisitha.user_service.repository;

import com.tisitha.user_service.model.ForgotPassword;
import com.tisitha.user_service.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForgotPassword fb WHERE fb.user.id = ?1")
    void deleteByUserId(UUID id);
}
