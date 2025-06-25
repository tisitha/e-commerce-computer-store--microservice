package com.tisitha.user_service.service;

import com.tisitha.user_service.dto.PasswordDTO;
import com.tisitha.user_service.dto.RegisterRequestDTO;
import com.tisitha.user_service.dto.UpdateUserDTO;
import com.tisitha.user_service.exception.EmailHasTakenException;
import com.tisitha.user_service.exception.PasswordIncorrectException;
import com.tisitha.user_service.exception.PasswordsNotMatchingException;
import com.tisitha.user_service.exception.UserNotFoundException;
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
    public String getEmailById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
        return user.getEmail();
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordRepeat())){
            throw new PasswordsNotMatchingException("passwords not matching");
        }
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new EmailHasTakenException("Email has taken");
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
    public void updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        if(!updateUserDTO.getPassword().equals(updateUserDTO.getPasswordRepeat())){
            throw new PasswordsNotMatchingException("passwords not matching");
        }
        if(userRepository.existsByEmailAndIdNot(updateUserDTO.getEmail(),id)){
            throw new EmailHasTakenException("Email has taken");
        }
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(updateUserDTO.getCurrentPassword(), user.getPassword())){
            throw new PasswordIncorrectException("Incorrect password");
        }
        user.setEmail(updateUserDTO.getEmail());
        if(!updateUserDTO.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setDob(updateUserDTO.getDob());
        user.setAddress1(updateUserDTO.getAddress1());
        user.setAddress2(updateUserDTO.getAddress2());
        user.setContactNo(updateUserDTO.getContactNo());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id, PasswordDTO pass) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(pass.password(), user.getPassword())){
            throw new PasswordIncorrectException("Incorrect password");
        }
        userRepository.deleteById(id);
    }

}
