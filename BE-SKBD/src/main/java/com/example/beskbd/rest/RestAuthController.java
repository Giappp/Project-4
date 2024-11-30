package com.example.beskbd.rest;

import com.example.beskbd.dto.request.*;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestAuthController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;


    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build());
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

    private String generateJwt(String email) {
        String Key = "c6a403d10f47e77cef70cce1025aebae59fee67cad7fd400941946a152eff881040d3fe1e29960648f0e7ed5246ef7bba5ea5bbea6accaf8730fea6884144ead849d546444e9a8629d11ce3224b6ed8438127b24dd134933d142aeb9a5fb8c78a782882e6ee2ff9242886a35f90f553f9b40f913e783101a172a45e91983e2716f4715c2aed52172022d320315ec0186e5d19a50dfc1f14199adb6b71ad57534e15662ec386b825a01fe32741ff329d2efa34c32212058f7538ff4ca627413445fa3d91731d11b5a6080798ac3613547a91996d39b9ef29974abde090a6ccd7a21cd853c053c1671af614bf38e036975945cade7395112e36b31c650bfc42a71";
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3000000000L)) // Set expiration time
                .signWith(SignatureAlgorithm.ES256, Key)
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
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest requestBody,
                                                            HttpServletRequest request) {
        userService.forgotPassword(requestBody.getEmail(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder().build());
    }

}
