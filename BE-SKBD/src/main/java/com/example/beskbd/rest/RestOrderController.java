package com.example.beskbd.rest;

import com.example.beskbd.dto.request.OrderCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Order;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestOrderController {
    public static Logger logger = LoggerFactory.getLogger(RestOrderController.class);
    @Autowired
    OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreationRequest request) {
        try {
            logger.info("Creating order with details: {}", request);
            orderService.addOrder(request);
            logger.info("Order created successfully");
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .build());
        } catch (AppException e) {
            // Handle the specific exception and return a proper error response
            logger.error("Error creating order: {}", e.getMessage());
            return ResponseEntity.status(e.getErrorCode().getStatusCode())  // Extract status code
                    .body(ApiResponse.builder()
                            .success(false)
                            .errorCode(e.getErrorCode().getCode())  // Extract the integer code
                            .errorMessage(e.getErrorCode().getMessage())
                            .build());
        }
    }




    @GetMapping("/getOrders")
    public ResponseEntity<ApiResponse> getOrders() {
        logger.info("Fetching new orders");
        List<Order> newOrders = orderService.getAllOrders(); // Adjust this according to your service method
        return ResponseEntity.ok(ApiResponse.builder()
                .data(newOrders)
                .success(true)
                .build());
    }
}