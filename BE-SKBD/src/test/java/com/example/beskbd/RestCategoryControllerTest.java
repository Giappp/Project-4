package com.example.beskbd;
// Generated by Qodo Gen


import com.example.beskbd.dto.request.CategoryCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.rest.RestCategoryController;
import com.example.beskbd.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BeSkbdApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@ExtendWith(MockitoExtension.class)
public class RestCategoryControllerTest {
    @Mock
    CategoryService categoryService;
    @InjectMocks
    RestCategoryController controller;
    @Mock
    CategoryCreationRequest request;
    @BeforeEach
    public void setUp() {
        // Mock the CategoryService
        categoryService = Mockito.mock(CategoryService.class);
        // Initialize the controller with the mocked service
        controller = new RestCategoryController(categoryService);
    }
    @Test
    public void test_create_category_with_null_values() {

       request.builder()
            .categoryName(null)
            .categoryDescription("All electronic items")
            .gender(null)
            .productType(null)
            .build();

        Exception exception = assertThrows(Exception.class, () -> {
            controller.createCategory(request);
        });

        String expectedMessage = "must not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Validate request body using @Valid annotation
    @Test
    public void test_create_category_with_invalid_data() {
            request.builder()
            .categoryName(null)  // Invalid data: categoryName is null
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();

        Exception exception = assertThrows(MethodArgumentNotValidException.class, () -> {
            controller.createCategory(request);
        });

        String expectedMessage = "categoryName: must not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Ensure CategoryService is correctly injected into RestCategoryController
    @Test
    public void test_category_service_injection() {

        assertNotNull(controller);
        assertEquals(categoryService, controller.categoryService);
    }

    // Return a successful response when category creation is completed
    @Test
    public void test_create_category_successful_response() {

        request.builder()
            .categoryName("Books")
            .categoryDescription("All kinds of books")
            .gender("UNISEX")
            .productType("LITERATURE")
            .build();

        ResponseEntity<?> response = controller.createCategory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    // Return appropriate error response for invalid input data

    // Verify that the CategoryService's createNewCategory method is called once per request
    @Test
    public void test_create_new_category_called_once() {
        request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();

        controller.createCategory(request);

        Mockito.verify(categoryService, times(1)).createNewCategory(request);
    }

    // Handle invalid enum values for gender in CategoryCreationRequest
    @Test
    public void test_create_category_with_invalid_gender() {
       request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("INVALID_GENDER")
            .productType("GADGETS")
            .build();
    
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createCategory(request);
        });
    
        String expectedMessage = "No enum constant";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Ensure thread safety when handling concurrent requests
    @Test
    public void test_concurrent_category_creation_requests() throws InterruptedException {
            request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();
    
        Runnable task = () -> {
            ResponseEntity<?> response = controller.createCategory(request);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
            assertNotNull(apiResponse);
            assertTrue(apiResponse.getSuccess());
        };
    
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
    
        thread1.start();
        thread2.start();
    
        thread1.join();
        thread2.join();
    }

    // Validate that the response contains success message upon category creation
    @Test
    public void test_create_category_returns_success_message() {
        request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();
    
        ResponseEntity<?> response = controller.createCategory(request);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    // Check for correct HTTP status code in the response
    @Test
    public void test_create_category_returns_ok_status() {
        request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();

        ResponseEntity<?> response = controller.createCategory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    // Successfully create a category with valid input data
    @Test
    public void test_create_category_with_valid_data() {
        request.builder()
            .categoryName("Electronics")
            .categoryDescription("All electronic items")
            .gender("UNISEX")
            .productType("GADGETS")
            .build();

        ResponseEntity<?> response = controller.createCategory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }
}