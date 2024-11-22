package com.example.beskbd;
// Generated by Qodo Gen

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.example.beskbd.dto.request.OrderCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Order;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.rest.RestOrderController;

import com.example.beskbd.services.OrderService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BeSkbdApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@ExtendWith(MockitoExtension.class)
public class RestOrderControllerTest {

    @Autowired
    RestOrderController restOrderController;// Creating an order with valid data returns a successful response
    @Test
    public void test_create_order_with_valid_data() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);
    
        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);
    
        // Act
        ResponseEntity<?> response = controller.createOrder(request);
    
        // Assert
        assertNotNull(response);
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("success"));
    }

    // Creating an order with null request throws an exception
    @Test
    public void test_create_order_with_null_request() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);
    
        // Act & Assert
        assertThrows(AppException.class, () -> controller.createOrder(null));
    }

    // Fetching all orders returns a list of orders
    @Test
    public void test_fetch_all_orders_returns_list_of_orders() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);
        List<Order> mockOrders = List.of(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(mockOrders);

        // Act
        ResponseEntity<ApiResponse> response = controller.getOrders();

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        assertEquals(mockOrders, response.getBody().getData());
    }

    // Logging occurs when fetching orders
    @Test
    public void test_logging_when_fetching_orders() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        List<Order> mockOrders = List.of(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(mockOrders);
        RestOrderController controller = new RestOrderController(orderService);
        Logger logger = LoggerFactory.getLogger(RestOrderController.class);
        Appender<ILoggingEvent> mockAppender = mock(Appender.class);
        ((ch.qos.logback.classic.Logger) logger).addAppender(mockAppender);
    
        // Act
        controller.getOrders();
    
        // Assert
        verify(mockAppender).doAppend(argThat(event -> event.getFormattedMessage().contains("Fetching new orders")));
    }

    // The order creation request is correctly passed to the service layer
    @Test
    public void test_create_order_request_passed_to_service() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);

        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);

        // Act
        controller.createOrder(request);

        // Assert
        verify(orderService, times(1)).addOrder(request);
    }

    // Creating an order with invalid orderItemsID throws an exception
    @Test
    public void test_create_order_with_invalid_orderItemsID_throws_exception() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(-1L); // Invalid ID

        OrderService orderService = mock(OrderService.class);
        doThrow(new AppException(ErrorCode.INVALID_REQUEST)).when(orderService).addOrder(request);
        RestOrderController controller = new RestOrderController(orderService);

        // Act & Assert
        assertThrows(AppException.class, () -> controller.createOrder(request));
    }

    // Creating an order with missing user information throws an exception
    @Test
    public void test_create_order_with_missing_user_information() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);

        OrderService orderService = mock(OrderService.class);
        doThrow(new AppException(ErrorCode.INVALID_REQUEST)).when(orderService).addOrder(request);
        RestOrderController controller = new RestOrderController(orderService);

        // Act & Assert
        assertThrows(AppException.class, () -> controller.createOrder(request));
    }

    // Fetching orders when none exist returns an empty list
    @Test
    public void test_get_orders_returns_empty_list_when_no_orders_exist() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());
        RestOrderController controller = new RestOrderController(orderService);

        // Act
        ResponseEntity<ApiResponse> response = controller.getOrders();

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        assertTrue(((List<Order>) response.getBody().getData()).isEmpty());
    }

    // The response for order creation includes a success flag
    @Test
    public void test_create_order_response_includes_success_flag() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);

        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);

        // Act
        ResponseEntity<ApiResponse> response = controller.createOrder(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    // The response for fetching orders includes the order data
    @Test
    public void test_get_orders_includes_order_data() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);
        List<Order> mockOrders = List.of(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(mockOrders);

        // Act
        ResponseEntity<ApiResponse> response = controller.getOrders();

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        assertEquals(mockOrders, response.getBody().getData());
    }

    // Order fetching logs the action
    @Test
    public void test_fetch_orders_logs_action() {
        // Arrange
        OrderService orderService = mock(OrderService.class);
        List<Order> orders = List.of(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(orders);
        RestOrderController controller = new RestOrderController(orderService);
        Logger logger = mock(Logger.class);
        restOrderController.logger = logger;

        // Act
        ResponseEntity<ApiResponse> response = controller.getOrders();

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        verify(logger).info("Fetching new orders");
    }

    // The controller handles exceptions from the service layer gracefully
    @Test
    public void test_create_order_handles_service_exception() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);

        OrderService orderService = mock(OrderService.class);
        doThrow(new AppException(ErrorCode.INVALID_REQUEST)).when(orderService).addOrder(request);
        RestOrderController controller = new RestOrderController(orderService);

        // Act
        ResponseEntity<ApiResponse> response = controller.createOrder(request);

        // Assert
        assertNotNull(response);
        assertFalse(Objects.requireNonNull(response.getBody()).getSuccess());
        assertEquals(ErrorCode.INVALID_REQUEST, response.getBody().getErrorCode());
    }

    // Order creation logs the request details
    @Test
    public void test_order_creation_logs_request_details() {
        // Arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setUserId(new User());
        request.setProductDescription("Sample Product");
        request.setShippingAddress("123 Sample Street");
        request.setOrderItemsID(1L);

        OrderService orderService = mock(OrderService.class);
        RestOrderController controller = new RestOrderController(orderService);

        Logger logger = mock(Logger.class);
        RestOrderController.logger = logger;

        // Act
        controller.createOrder(request);

        // Assert
        verify(logger).info("Creating order with details: {}", request);
    }
}