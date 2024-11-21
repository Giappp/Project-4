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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BeSkbdApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class Accounttestapi {

    @Test
    public void test_get_details_authenticated_user() {
        // Arrange
        User mockUser = new User();
        mockUser.setEnabled(true);
        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setUsername("johndoe");
        mockUser.setAuthority(Role.ROLE_USER);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);

        AccountService accountService = Mockito.mock(AccountService.class);
        RestAccountController controller = new RestAccountController(accountService);

        // Act
        ApiResponse<?> response = controller.getDetails();

        // Assert
        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());
    }

    @Test
    public void test_get_details_null_authentication() {
        // Arrange
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Mock the account service
        AccountService accountService = Mockito.mock(AccountService.class);
        // No need to stub accountService methods here since we expect an exception

        RestAccountController controller = new RestAccountController(accountService);

        // Act & Assert
        AppException exception = assertThrows(AppException.class, controller::getDetails);
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
        User mockUser = new User();
        mockUser.setEnabled(true);
        mockUser.setEmail(null);
        mockUser.setFirstName(null);
        mockUser.setLastName("Doe");
        mockUser.setUsername("johndoe");
        mockUser.setAuthority(Role.ROLE_USER);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);

        AccountService accountService = Mockito.mock(AccountService.class);
        RestAccountController controller = new RestAccountController(accountService);

        ApiResponse<?> response = controller.getDetails();

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertNotNull(response.getData());
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
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        AccountService accountService = Mockito.mock(AccountService.class);
        RestAccountController controller = new RestAccountController(accountService);

        AppException exception = assertThrows(AppException.class, controller::getDetails);
        assertEquals(ErrorCode.UNAUTHENTICATED, exception.getErrorCode());
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