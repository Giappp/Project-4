package com.example.beskbd.rest;

import com.example.beskbd.dto.request.*;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600000)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestAuthController {

    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse result = authenticationService.authenticate(request);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<AuthenticationResponse>builder()
                                .errorMessage("Invalid credentials")
                                .errorCode(401)
                                .build());
            }

            return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                    .data(result)
                    .success(true)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AuthenticationResponse>builder()
                            .errorMessage("Authentication failed: " + e.getMessage())
                            .errorCode(500)
                            .build());
        }
    }

    @GetMapping("/oauth2/login")
    public ResponseEntity<String> oauth2Login() {
        return ResponseEntity.ok("Please login using Google OAuth2.");
    }

    @GetMapping("/oauth2/callback")
    public ResponseEntity<String> oauth2Callback(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing OAuth2 callback: OIDC user is null.");
        }

        String email = oidcUser.getEmail();
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error processing OAuth2 callback: Email not found in OIDC user.");
        }

        String jwt = generateJwt(email);
        return ResponseEntity.ok("JWT: " + jwt);
    }

    private String generateJwt(String email) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3000000L)) // 50 minutes
                .signWith(key)
                .compact();
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> registerUser(@RequestBody @Valid UserCreationRequest request) {
        AuthenticationResponse result = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .success(true)
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody RefreshRequest request) {
        AuthenticationResponse result = authenticationService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .success(true)
                .build());
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest requestBody,
                                                              HttpServletRequest request) {
        userService.forgotPassword(requestBody.getEmail(), request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .data("Password reset link sent successfully.")
                .success(true)
                .build());
    }

}