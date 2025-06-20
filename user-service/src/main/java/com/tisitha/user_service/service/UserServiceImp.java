package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.model.User;
import com.tisitha.user_service.model.UserRole;
import com.tisitha.user_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordRepeat())){
            throw new RuntimeException("password not matching");
        }
        User user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setDob(registerRequestDTO.getDob());
        user.setAddress1(registerRequestDTO.getAddress1());
        user.setAddress2(registerRequestDTO.getAddress2());
        user.setContactNo(registerRequestDTO.getContactNo());
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);
    }

    @Override
    public void updatePassword(String email, String password) {
        userRepository.updatePassword(email,password);
    }

    @Override
    public void updateUser(UUID id, RegisterRequestDTO registerRequestDTO) {
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordRepeat())){
            throw new RuntimeException("password not matching");
        }
        User user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setDob(registerRequestDTO.getDob());
        user.setAddress1(registerRequestDTO.getAddress1());
        user.setAddress2(registerRequestDTO.getAddress2());
        user.setContactNo(registerRequestDTO.getContactNo());
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("user not found");
        }
        userRepository.deleteById(id);
    }

}
