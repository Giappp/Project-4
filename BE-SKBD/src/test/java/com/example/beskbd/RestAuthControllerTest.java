package com.example.beskbd;

import com.example.beskbd.dto.request.AuthenticationRequest;
import com.example.beskbd.dto.request.LogoutRequest;
import com.example.beskbd.dto.request.RefreshRequest;
import com.example.beskbd.dto.request.UserCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.rest.RestAuthController;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BeSkbdApplication.class)
@AutoConfigureMockMvc
public class RestAuthControllerTest {
    @Mock
    OidcUser oidcUser;
    @Value("${application.security.jwt.secret-key-v2}")
    private String Key;
    @InjectMocks
    private RestAuthController controller;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationRequest authenticationRequest;
    @Mock
    private LogoutRequest logoutRequest;
    @Mock
    private UserCreationRequest userCreationRequest;

    @BeforeEach
    public void setUp() {
        authenticationRequest = AuthenticationRequest.builder()
                .username("validUser")
                .password("validPassword")
                .build();

        logoutRequest = LogoutRequest.builder()
                .token("validToken")
                .build();

        userCreationRequest = UserCreationRequest.builder()
                .username("newUser")
                .password("validPassword123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .address("123 Main St")
                .build();
    }

    @Test
    public void test_authenticate_with_valid_credentials() {
        // Create a valid AuthenticationResponse object
        AuthenticationResponse authResponse = new AuthenticationResponse("sampleToken", true);

        // Mock the behavior of the authentication service to return AuthenticationResponse directly
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authResponse);

        // Call the controller method
        var response = controller.authenticate(authenticationRequest);

        // Assertions
//        assertNotNull(response);
//        assertNotNull(response.getData());
//        assertTrue(response.getData().isAuthenticated());
//        assertEquals("sampleToken", response.getData().getToken());
    }

    @Test
    public void test_logout_with_valid_token() {
        ResponseEntity<?> response = controller.logout(logoutRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_register_user_with_valid_details() {
        // Mock the behavior of userService.createUser
        when(userService.createUser(any(UserCreationRequest.class)))
                .thenReturn(new AuthenticationResponse("sampleToken", true));

        // Call the registerUser method in the controller
        ApiResponse<AuthenticationResponse> response = controller.registerUser(userCreationRequest);

        // Assertions to verify the response
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().isAuthenticated());
        assertEquals("sampleToken", response.getData().getToken());
    }

    @Test
    public void test_oauth2_login_and_callback() {
        ResponseEntity<String> loginResponse = controller.oauth2Login();
        assertNotNull(loginResponse);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertEquals("Please login using OAuth2.", loginResponse.getBody());

        OidcUser mockOidcUser = mock(OidcUser.class);
        when(mockOidcUser.getEmail()).thenReturn("user@example.com");
        ResponseEntity<String> callbackResponse = controller.oauth2Callback(mockOidcUser);
        assertNotNull(callbackResponse);
        assertEquals(HttpStatus.OK, callbackResponse.getStatusCode());
        assertTrue(callbackResponse.getBody().startsWith("JWT: "));
    }

    @Test
    public void test_authenticate_with_invalid_credentials() {
        when(authenticationService.authenticate(any())).thenThrow(new AppException(ErrorCode.UNAUTHENTICATED));

        AppException exception = assertThrows(AppException.class, () -> {
            controller.authenticate(authenticationRequest);
        });

        assertEquals(ErrorCode.UNAUTHENTICATED, exception.getErrorCode());
    }

    @Test
    public void test_register_user_with_invalid_fields() {
        UserCreationRequest invalidRequest = UserCreationRequest.builder()
                .username("user") // Invalid: less than 6 characters
                .password("pass") // Invalid: less than 8 characters
                .build();

        ApiResponse<AuthenticationResponse> response = controller.registerUser(invalidRequest);

        assertNotNull(response);
        assertNull(response.getData());
        assertFalse(response.getSuccess());
        assertEquals(ErrorCode.INVALID_REQUEST, response.getErrorCode());
    }

    @Test
    public void test_oauth2_callback_with_invalid_oidc_user() {
        OidcUser invalidOidcUser = null; // Simulating an invalid OidcUser
        ResponseEntity<String> response = controller.oauth2Callback(invalidOidcUser);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error processing OAuth2 callback"));
    }

    @Test
    public void test_generate_jwt_with_valid_claims() {
        when(oidcUser.getEmail()).thenReturn("user@example.com");

        // Generate JWT
        String jwt = generateJwt(oidcUser.getEmail());
        System.out.println("Generated JWT: " + jwt);

        assertNotNull(jwt);

        // Validate JWT
        Claims claims = validateJwt(jwt);
        System.out.println("Claims: " + claims);

        assertEquals("user@example.com", claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    public String generateJwt(String email) {
        return Jwts.builder()
                .setSubject(email)  // Ensure this is being set correctly
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3000000000L)) // Example expiration
                .signWith(SignatureAlgorithm.HS256, Key)
                .compact();

    }

    public Claims validateJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(Key)
                .parseClaimsJws(jwt)
                .getBody();
    }

    @Test
    public void test_refresh_token_with_invalid_token() {
        RefreshRequest request = RefreshRequest.builder()
                .refreshToken("invalidToken")
                .build();

        when(authenticationService.refreshToken(request)).thenThrow(new AppException(ErrorCode.UNSUPPORTED_TOKEN));

        AppException exception = assertThrows(AppException.class, () -> {
            controller.refreshToken(request);
        });

        assertEquals(ErrorCode.UNSUPPORTED_TOKEN, exception.getErrorCode());
    }

    // Additional tests can be added similarly...
}