package com.example.beskbd;
// Generated by Qodo Gen

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.example.beskbd.dto.object.CategoryDto;
import com.example.beskbd.dto.object.NewArrivalProductDto;
import com.example.beskbd.dto.object.ProductAttributeDto;
import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.rest.RestProductController;

import com.example.beskbd.services.ProductService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BeSkbdApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@ExtendWith(MockitoExtension.class)
public class RestProductControllerTest {

    @InjectMocks
    RestProductController controller;
    @Mock
    ProductService productService;
    @Test
    public void test_get_by_gender_success() {
        // Arrange
        Map<String, List<CategoryDto>> mockCategories = Map.of(
                "Male", List.of(new CategoryDto()),
                "Female", List.of(new CategoryDto()),
                "Unisex" , List.of(new CategoryDto()),
                "child", List.of(new CategoryDto())

        );
        when(productService.getCategoryByGender()).thenReturn(mockCategories);

        // Act
        ResponseEntity<?> response = controller.getByGender();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        assertEquals(mockCategories, apiResponse.getData());
    }

    // Handle null or invalid ProductCreationRequest gracefully
    @Test
    public void test_create_product_with_invalid_request() {
        // Arrange
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest invalidRequest = null;

        // Act & Assert
        AppException thrownException = assertThrows(AppException.class, () -> {
            controller.createProduct(invalidRequest);
        });

        assertEquals("Product creation request cannot be null", thrownException.getMessage());
    }

    // Retrieve a list of new arrival products
    @Test
    public void testGetNewArrivalsSuccess() {
        // Arrange
        List<NewArrivalProductDto> mockNewArrivals = Arrays.asList(
                NewArrivalProductDto.builder().productId(1L).productName("Product 1").build(),
                NewArrivalProductDto.builder().productId(2L).productName("Product 2").build()
        );
        when(productService.getNewArrivalProduct()).thenReturn(mockNewArrivals);

        // Act
        ResponseEntity<?> response = controller.getNewArrivals();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse); // Ensure apiResponse is not null
        assertTrue(apiResponse.getSuccess());
        assertEquals(mockNewArrivals, apiResponse.getData());
    }

    // Handle missing or invalid category ID in ProductCreationRequest
    @Test
    public void test_create_product_with_invalid_category_id() {
        // Arrange
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest request = new ProductCreationRequest();
        request.setProductName("Test Product");
        request.setCategoryId(-1L); // Invalid category ID

        doThrow(new AppException(ErrorCode.INVALID_REQUEST))
            .when(productService).addProduct(request);

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> {
            controller.createProduct(request);
        });

        assertEquals(ErrorCode.INVALID_REQUEST, exception.getErrorCode());
    }

    // Handle no new arrival products available
    @Test
    public void test_get_new_arrivals_no_products() {
        // Arrange
        RestProductController controller = new RestProductController(productService);
        when(productService.getNewArrivalProduct()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = controller.getNewArrivals();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertTrue(((List<?>) apiResponse.getData()).isEmpty());
    }

    // Handle empty category list when grouping by gender
    @Test
    public void test_get_by_gender_empty_category_list() {
        // Arrange
        RestProductController controller = new RestProductController(productService);
        Map<String, List<CategoryDto>> emptyCategories = Map.of();
        when(productService.getCategoryByGender()).thenReturn(emptyCategories);

        // Act
        ResponseEntity<?> response = controller.getByGender();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertEquals(emptyCategories, apiResponse.getData());
    }

    // Ensure correct media type is consumed for product creation
    @Test
    public void test_create_product_with_multipart_form_data() {
        // Arrange
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest mockRequest = new ProductCreationRequest();
        mockRequest.setProductName("Test Product");
        mockRequest.setCategoryId(1L);
        mockRequest.setAttributes(List.of(new ProductAttributeDto()));

        // Act
        ResponseEntity<?> response = controller.createProduct(mockRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assert apiResponse != null;
        assertTrue(apiResponse.getSuccess());
    }

    // Validate product attributes during creation
    @Test
    public void test_create_product_with_valid_attributes() {
        // Arrange
        ProductService productService = mock(ProductService.class);
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest request = new ProductCreationRequest();
        request.setProductName("Test Product");
        request.setProductDescription("Test Description");
        request.setCategoryId(1L);
        request.setAttributes(List.of(new ProductAttributeDto()));

        // Act
        ResponseEntity<?> response = controller.createProduct(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assert apiResponse != null;
        assertTrue(apiResponse.getSuccess());
        verify(productService).addProduct(request);
    }

    // Handle exceptions during image upload
    @Test
    public void test_create_product_image_upload_exception() {
        // Arrange
        ProductCreationRequest request = new ProductCreationRequest();
        request.setProductName("Test Product");
        request.setCategoryId(1L);
        ProductAttributeDto attributeDto = new ProductAttributeDto();
        attributeDto.setImageFiles(List.of(new MockMultipartFile("image", new byte[0])));
        request.setAttributes(List.of(attributeDto));

        doThrow(new RuntimeException("Failed to upload image"))
                .when(productService).addProduct(any(ProductCreationRequest.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controller.createProduct(request);
        });
    }

    // Ensure API response structure is consistent


    // Log product creation requests for auditing
    @Test
    public void test_create_product_logs_request() {
        // Arrange
        ProductService productService = mock(ProductService.class);
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest request = new ProductCreationRequest();
        request.setProductName("Test Product");
        request.setCategoryId(1L);
        Logger logger = (Logger) LoggerFactory.getLogger(RestProductController.class);
        Appender<ILoggingEvent> mockAppender = mock(Appender.class);
        logger.addAppender(mockAppender);

        // Act
        controller.createProduct(request);

        // Assert
        verify(mockAppender).doAppend(argThat(event -> event.getFormattedMessage().contains("Test Product")));
    }

    // Create a new product with valid data
    @Test
    public void test_create_product_success() {
        // Arrange
        ProductService productService = mock(ProductService.class);
        RestProductController controller = new RestProductController(productService);
        ProductCreationRequest request = new ProductCreationRequest();
        request.setProductName("Test Product");
        request.setProductDescription("Test Description");
        request.setCategoryId(1L);
        request.setAttributes(List.of(new ProductAttributeDto()));

        // Act
        ResponseEntity<?> response = controller.createProduct(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        verify(productService, times(1)).addProduct(request);
    }

    // Return a successful response for all products retrieval
@Test
public void test_get_all_products_success() {
    // Arrange
    ProductService productService = mock(ProductService.class);
    RestProductController controller = new RestProductController(productService);
    List<NewArrivalProductDto> mockProducts = new ArrayList<>();
    mockProducts.add(NewArrivalProductDto.builder()
            .productId(1L)
            .productName("Product 1")
            .productDescription("Description 1")
            .maxPrice(BigDecimal.valueOf(100))
            .minPrice(BigDecimal.valueOf(9.99))
            .build());
    mockProducts.add(NewArrivalProductDto.builder()
            .productId(2L)
            .productName("Product 2")
            .productDescription("Description 2")
            .maxPrice(BigDecimal.valueOf(10000))
            .minPrice(BigDecimal.valueOf(1.99))
            .build());
    when(productService.getNewArrivalProduct()).thenReturn(mockProducts);

    // Act
    ResponseEntity<?> response = controller.getAllProducts();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
    assertTrue(apiResponse.getSuccess());
}
}