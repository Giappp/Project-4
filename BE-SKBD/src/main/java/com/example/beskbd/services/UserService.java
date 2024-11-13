package com.example.beskbd.services;

import com.example.beskbd.dto.request.UserCreationRequest;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.entities.Role;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//implements UserDetailsService
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    EmailService emailService;
    BCryptPasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
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
        CompletableFuture<String> token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    public void forgotPassword(String email) {
        log.info("Requesting password reset for email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
            userRepository.save(user);

            String resetUrl = "http://localhost:8083/reset-password?token=" + token;
            emailService.sendResetLink(email, "Password Reset Request", "Click the link (valid for 1 hour) to reset your password: " + resetUrl);
        } else {
            log.error("User not found for email: {}", email);
            throw new AppException(ErrorCode.USER_NOT_EXISTED);


        }
    }
}
