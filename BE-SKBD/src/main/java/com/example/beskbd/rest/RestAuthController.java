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
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600000)
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class RestAuthController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse result = authenticationService.authenticate(request);

            if (result == null) {
                // Return a 401 Unauthorized if authentication fails
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<AuthenticationResponse>builder()
                                .errorMessage("Invalid credentials")
                                .errorCode(401) // Optional: Include error codes in the response
                                .build());
            }

            // Return 200 OK if authentication is successful
            return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                    .data(result)
                    .success(true)
                    .build());
        } catch (Exception e) {
            // Return 500 Internal Server Error for unexpected exceptions
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                    .body(ApiResponse.<AuthenticationResponse>builder()
                            .errorMessage("Authentication failed: " + e.getMessage())
                            .errorCode(400) // Optional: Include error codes in the response
                            .build());
        }
    }

    @GetMapping("/oauth2/login")
    public ResponseEntity<String> oauth2Login() {
        return ResponseEntity.ok("Please login using OAuth2.");
    }
    @GetMapping("/oauth2/callback")
    public ResponseEntity<String> oauth2Callback(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing OAuth2 callback");
        }

        // Extract the email or other claims you need from the OidcUser
        String email = oidcUser.getEmail();
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found in OIDC user");
        }

        // Generate the JWT
        String jwt = generateJwt(email);

        // Return the JWT in the response
        return ResponseEntity.ok("JWT: " + jwt);
    }

    public String generateJwt(String email) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Use HS256 for simplicity
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3000000L)) // 50 minutes
                .signWith(key)
                .compact();
    }

    @PostMapping("/registration")
    public ApiResponse<AuthenticationResponse> registerUser(@RequestBody @Valid UserCreationRequest request) {
        var result = userService.createUser(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }


    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request) {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().data(result).build();
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder().build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest requestBody,
                                                            HttpServletRequest request) {
        userService.forgotPassword(requestBody.getEmail(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .data("Password reset link sent successfully.") // Non-null placeholder value
                        .success(true)
                        .build());
    }


}
