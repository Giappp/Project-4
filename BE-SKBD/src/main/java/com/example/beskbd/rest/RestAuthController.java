package com.example.beskbd.rest;

import com.example.beskbd.dto.request.AuthenticationRequest;
import com.example.beskbd.dto.request.LogoutRequest;
import com.example.beskbd.dto.request.UserCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.UserRepository;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestAuthController {
    AuthenticationService authenticationService;
    UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse result = authenticationService.authenticate(request);

            if (result == null) {
                return ApiResponse.<AuthenticationResponse>builder()
                        .errorMessage("Invalid credentials")
                        .build();
            }

            return ApiResponse.<AuthenticationResponse>builder()
                    .data(result)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .errorMessage("Authentication failed: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/registration")
    public ApiResponse<AuthenticationResponse> registerUser(@RequestBody @Valid UserCreationRequest request) {
        var result = userService.createUser(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/signout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email"); // Extract email from the request body

        // Validate user existence
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Generate a password reset token
        String resetToken = UUID.randomUUID().toString(); // Generate a unique token
        user.setResetToken(resetToken); // Set the reset token on the user entity

        // Save the user with the reset token
        userService.save(user);

        // Send the reset password email with the link
        String resetLink = String.format("http://localhost:8083/auth/reset-password?token=%s", resetToken);
        authenticationService.sendPasswordResetEmail(user.getEmail(), resetLink);

        return ApiResponse.<String>builder()
                .data("Password reset link has been sent to your email.")
                .build();
    }
    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        User user = userService.findByResetToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

        // Validate the new password
        authenticationService.validatePassword(newPassword);

        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear the reset token
        userService.save(user); // Save the updated user

        return ApiResponse.<String>builder()
                .data("Your password has been successfully reset.")
                .build();
    }
}
