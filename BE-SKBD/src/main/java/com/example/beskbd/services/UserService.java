package com.example.beskbd.services;

import com.example.beskbd.dto.request.UserCreationRequest;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.entities.Role;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserDetailsService {
    static Logger logger = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtService jwtService;
    EmailService emailService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public AuthenticationResponse createUser(UserCreationRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .authority(Role.ROLE_USER)
                .isEnabled(true)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void forgotPassword(String email, HttpServletRequest request) {
        logger.info("Requesting password reset for email: {}", email);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTS));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        userRepository.save(user);
        String resetUrl = "http://localhost:8083/reset-password?token=" + token;
        emailService.sendResetLink(email, "Password Reset Request", "Click the link (valid for 1 hour) to reset your password: " + resetUrl);
    }
}
