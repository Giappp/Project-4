package com.example.beskbd;

import com.example.beskbd.dto.request.PromotionCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.PromotionDTO;
import com.example.beskbd.entities.PromotionProduct;
import com.example.beskbd.rest.RestPromotionController;
import com.example.beskbd.services.PromotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestPromotionControllerTest {

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private RestPromotionController restPromotionController; // Use real instance with injected service

    // Create promotion with valid request returns success response
    @Test
    void test_create_promotion_success() {
        PromotionCreationRequest request = new PromotionCreationRequest();
        request.setDescription("Test Promotion");
        request.setStartDate(LocalDateTime.now());
        request.setEndDate(LocalDateTime.now().plusDays(7));
        request.setPromotionProductList(Collections.emptyList());

        // Mocking the expected return value
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(1L);
        promotionDTO.setDescription("Test Promotion");

        when(promotionService.createPromotion(any(PromotionCreationRequest.class))).thenReturn(promotionDTO);

        // Act
        ResponseEntity<?> response = restPromotionController.createPromotion(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertEquals(promotionDTO, apiResponse.getData());
        verify(promotionService).createPromotion(request);
    }

    // Create promotion with invalid date range
    @Test
    void test_create_promotion_with_invalid_date_range() {
        PromotionCreationRequest request = new PromotionCreationRequest();
        request.setDescription("Invalid Date Range Promotion");
        request.setStartDate(LocalDateTime.now().plusDays(1)); // Start date is after end date
        request.setEndDate(LocalDateTime.now());
        request.setPromotionProductList(Collections.emptyList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restPromotionController.createPromotion(request);
        });

        assertEquals("Invalid date range: Start date must be before end date", exception.getMessage());
        verify(promotionService, never()).createPromotion(any(PromotionCreationRequest.class));
    }

    // Get promotion products by valid ID returns list of products with success status
    @Test
    void test_get_promotion_products_by_valid_id() {
        Long promotionId = 1L;
        List<PromotionProduct> promotionProducts = List.of(new PromotionProduct(), new PromotionProduct());
        when(promotionService.getPromotionProducts(promotionId)).thenReturn(promotionProducts);

        // Act
        ResponseEntity<?> response = restPromotionController.getPromotionProducts(promotionId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertEquals(promotionProducts, apiResponse.getData());
        verify(promotionService).getPromotionProducts(promotionId);
    }

    // Get all promotions returns list of promotions with success status
    @Test
    void test_get_all_promotions_success() {
        // Arrange
        List<PromotionDTO> promotions = List.of(new PromotionDTO(), new PromotionDTO());
        when(promotionService.getAllPromotions()).thenReturn(promotions);

        // Act
        ResponseEntity<?> response = restPromotionController.getAllPromotions();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertEquals(promotions, apiResponse.getData());
        verify(promotionService).getAllPromotions();
    }

    // Handle concurrent requests to create promotions
    @Test
    void test_create_promotion_concurrent_requests() throws InterruptedException {
        PromotionCreationRequest request = new PromotionCreationRequest();
        request.setDescription("Concurrent Promotion");
        request.setStartDate(LocalDateTime.now());
        request.setEndDate(LocalDateTime.now().plusDays(7));
        request.setPromotionProductList(Collections.emptyList());

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(1L);
        when(promotionService.createPromotion(any(PromotionCreationRequest.class))).thenReturn(promotionDTO);

        Runnable task = () -> {
            ResponseEntity<?> response = restPromotionController.createPromotion(request);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
            assertTrue(apiResponse.getSuccess());
        };

        // Act
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // Assert
        verify(promotionService, times(2)).createPromotion(request);
    }

    // Handle large lists of promotions/products efficiently
    @Test
    void test_get_all_promotions_with_large_list() {
        // Arrange
        List<PromotionDTO> largePromotionList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            PromotionDTO promotionDTO = new PromotionDTO();
            promotionDTO.setId((long) i);
            promotionDTO.setDescription("Promotion " + i);
            largePromotionList.add(promotionDTO);
        }

        when(promotionService.getAllPromotions()).thenReturn(largePromotionList);

        // Act
        ResponseEntity<?> response = restPromotionController.getAllPromotions();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertTrue(apiResponse.getSuccess());
        assertEquals(1000, ((List<?>) apiResponse.getData()).size());
        verify(promotionService).getAllPromotions();
    }
}