package com.example.beskbd.services;

import com.example.beskbd.dto.request.AuthenticationRequest;
import com.example.beskbd.dto.request.LogoutRequest;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            var user = (UserDetails) auth.getPrincipal();
            String token = request.getToken();
            if (!jwtService.validateToken(token, user)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            jwtService.invalidateToken(token);
            SecurityContextHolder.clearContext();
        }
    }
<<<<<<< Updated upstream
=======

    public AuthenticationResponse processRequest(String body) {
        return null;
    }
    public void forgotPassword(String email) {
        // Fetch the user by email
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Generate a password reset token (this could be a UUID or a secure random string)
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user); // Save the user with the reset token

        // Send an email with the reset link
        sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    public void validatePassword(String password) {

        if (password.length() < 8) {
            throw new AppException(ErrorCode.PASSWORD_TOO_SHORT);
        }

    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        String subject = "Password Reset Request";
        String resetLink = "https://yourapp.com/reset-password?token=" + resetToken;
        String body = "To reset your password, please click the link below:\n" + resetLink;

        emailService.sendEmail(email, subject, body);
    }
>>>>>>> Stashed changes
}
