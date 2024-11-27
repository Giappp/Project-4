package com.example.beskbd;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.example.beskbd.dto.response.AccountResponse;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Role;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.rest.RestAccountController;
import com.example.beskbd.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestAccountController {

    @Test
    public void test_get_details_authenticated_user() {
        // Arrange: Create a mock user
        User mockUser = new User();
        mockUser.setEnabled(true);
        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setUsername("johndoe");
        mockUser.setAuthority(Role.ROLE_USER);

        // Mock Authentication and SecurityContext
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Mock AccountService to return a valid response
        AccountResponse mockAccountResponse = new AccountResponse();
        mockAccountResponse.setEmail("test@example.com");
        mockAccountResponse.setFirstName("John");
        mockAccountResponse.setLastName("Doe");
        mockAccountResponse.setName("johndoe");

        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.getAccountDetails()).thenReturn(mockAccountResponse);

        // Create the controller with the mocked service
        RestAccountController controller = new RestAccountController(accountService);

        // Act: Call the getDetails method
        ApiResponse<?> response = controller.getDetails();

        // Assert: Verify the response is not null and contains the correct data
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());

        // Optionally, verify the data
        AccountResponse accountData = (AccountResponse) response.getData();
        assertEquals("johndoe", accountData.getName());
        assertEquals("test@example.com", accountData.getEmail());
        assertEquals("John", accountData.getFirstName());
        assertEquals("Doe", accountData.getLastName());
    }

    @Test
    public void test_getAccountDetails_unauthenticated() {
        // Arrange: Mock SecurityContext and set authentication to null
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Create AccountService instance
        AccountService accountService = new AccountService();

        // Act & Assert: Expect AppException
        AppException exception = assertThrows(AppException.class, accountService::getAccountDetails);

        // Assert the exception details
        assertEquals(ErrorCode.UNAUTHENTICATED, exception.getErrorCode());
    }

    @Test
    public void test_get_details_successful_retrieval() {
        AccountResponse mockAccountResponse = AccountResponse.builder()
                .activated(true)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .authorities(List.of("ROLE_USER"))
                .login("johndoe")
                .imageUrl(null)
                .build();

        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.getAccountDetails()).thenReturn(mockAccountResponse);
        RestAccountController controller = new RestAccountController(accountService);

        ApiResponse<?> response = controller.getDetails();

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());
    }

    @Test
    public void test_logs_info_message_on_account_details_request() {
        AccountService accountService = Mockito.mock(AccountService.class);
        RestAccountController controller = new RestAccountController(accountService);

        Logger logger = (Logger) LoggerFactory.getLogger(RestAccountController.class);
        Appender<ILoggingEvent> mockAppender = Mockito.mock(Appender.class);
        logger.addAppender(mockAppender);
        Mockito.doNothing().when(mockAppender).doAppend(any());

        controller.getDetails();

        Mockito.verify(mockAppender).doAppend(argThat(event -> event.getFormattedMessage().contains("Account details request")));
    }

    @Test
    public void test_get_details_with_incomplete_user_details() {
        // Create a mock user with incomplete details
        User mockUser = new User();
        mockUser.setEnabled(true);
        mockUser.setEmail(null); // Email is null
        mockUser.setFirstName(null); // First name is null
        mockUser.setLastName("Doe");
        mockUser.setUsername("johndoe");
        mockUser.setAuthority(Role.ROLE_USER);

        // Mock the AccountService
        AccountService accountService = Mockito.mock(AccountService.class);

        // Mocking the authentication and security context
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Mock the service response to return an AccountResponse
        AccountResponse mockResponse = AccountResponse.builder()
                .activated(mockUser.isEnabled())
                .email(mockUser.getEmail()) // This will be null
                .firstName(mockUser.getFirstName()) // This will be null
                .lastName(mockUser.getLastName())
                .authorities(List.of(mockUser.getAuthority().getAuthority()))
                .login(mockUser.getUsername())
                .imageUrl(mockUser.getImageUrl()) // Assuming getImageUrl() method exists
                .build();

        Mockito.when(accountService.getAccountDetails()).thenReturn(mockResponse);

        // Create the controller with the mocked service
        RestAccountController controller = new RestAccountController(accountService);

        // Call the method under test
        ApiResponse<?> response = controller.getDetails();

        // Assertions
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData()); // Ensure that data is not null

        // Optionally, check specific fields in the response if needed
        AccountResponse data = (AccountResponse) response.getData();
        assertEquals("johndoe", data.getLogin());
        assertNull(data.getEmail()); // Check that email is null
        assertNull(data.getFirstName()); // Check that first name is null
    }

    @Test
    public void test_account_service_injection() {
        AccountService accountService = Mockito.mock(AccountService.class);
        RestAccountController controller = new RestAccountController(accountService);

        ApiResponse<?> response = controller.getDetails();

        assertNotNull(response);
        Mockito.verify(accountService).getAccountDetails();
    }

    @Test
    public void test_endpoint_mapping_for_get_details() {
        RequestMapping requestMapping = RestAccountController.class.getAnnotation(RequestMapping.class);
        GetMapping getMapping = null;
        try {
            getMapping = RestAccountController.class.getMethod("getDetails").getAnnotation(GetMapping.class);
        } catch (NoSuchMethodException e) {
            fail("Method getDetails not found");
        }
        assertNotNull(requestMapping);
        assertEquals("/account", requestMapping.value()[0]);
        assertNotNull(getMapping);
    }

    @Test
    public void test_get_details_unauthenticated_user() {
        // Mock the SecurityContext to simulate an unauthenticated user
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Use the real AccountService
        AccountService accountService = new AccountService();

        // Create the controller with the real AccountService
        RestAccountController controller = new RestAccountController(accountService);

        // Expect AppException to be thrown
        AppException exception = assertThrows(AppException.class, controller::getDetails);

        // Check that the error code is UNAUTHENTICATED
        assertEquals(ErrorCode.UNAUTHENTICATED, exception.getErrorCode());

        // Clear the SecurityContext after the test
        SecurityContextHolder.clearContext();
    }
    @Test
    public void test_logger_initialization() {
        Logger logger = (Logger) LoggerFactory.getLogger(RestAccountController.class);
        assertNotNull(logger);
        assertEquals("com.example.beskbd.rest.RestAccountController", logger.getName());
    }

    @Test
    public void test_get_details_populates_data_field() {
        // Arrange
        User mockUser = new User();
        mockUser.setEnabled(true);
        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setUsername("johndoe");
        mockUser.setAuthority(Role.ROLE_USER);

//        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext); // Ensure the context is set

        AccountResponse accountResponse = AccountResponse.builder()
                .activated(true)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .authorities(List.of("ROLE_USER"))
                .login("johndoe")
                .imageUrl(null)
                .build();

        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.getAccountDetails()).thenReturn(accountResponse);

        RestAccountController controller = new RestAccountController(accountService);

        // Act
        ApiResponse<?> response = controller.getDetails();

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());
        assertEquals(accountResponse, response.getData());

        // Verify that the accountService method was called
        Mockito.verify(accountService).getAccountDetails();
    }
}