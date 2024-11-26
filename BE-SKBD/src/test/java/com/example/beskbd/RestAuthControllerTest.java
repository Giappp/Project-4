 // Use MockitoExtension for Mockito support
package com.example.beskbd;

import com.example.beskbd.dto.request.AuthenticationRequest;
import com.example.beskbd.dto.request.ForgotPasswordRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.rest.RestAuthController;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 @ExtendWith(MockitoExtension.class)
public class RestAuthControllerTest {

    @InjectMocks
    private RestAuthController restAuthController; // Inject mocked dependencies

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @Test
    public void test_successful_authentication_returns_valid_response() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("validUser", "validPassword");
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .token("validToken")
                .authenticated(true)
                .build();
        when(authenticationService.authenticate(request)).thenReturn(expectedResponse);

        // Act
        ApiResponse<AuthenticationResponse> response = restAuthController.authenticate(request).getBody();

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response.getData());
        assertNull(response.getErrorMessage());
    }
    @Test
    public void test_oauth2_callback_returns_jwt_for_valid_oidc_user() {
        // Arrange
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn("test@example.com");

        // Act
        ResponseEntity<String> response = restAuthController.oauth2Callback(oidcUser);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().startsWith("JWT:"));
    }
     @Test
     public void test_forgot_password_sends_reset_link() {
         // Arrange
         String email = "user@example.com";
         ForgotPasswordRequest request = new ForgotPasswordRequest();
         request.setEmail(email);
         HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

         doNothing().when(userService).forgotPassword(email, httpServletRequest);

         // Act
         ResponseEntity<ApiResponse<String>> response = restAuthController.forgotPassword(request, httpServletRequest);

         // Assert
         assertNotNull(response);
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("Password reset link sent successfully.", response.getBody().getData()); // Verify non-null 'data'
         assertNull(response.getBody().getErrorMessage());
     }
}