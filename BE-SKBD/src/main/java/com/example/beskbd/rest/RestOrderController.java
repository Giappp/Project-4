package com.example.beskbd.rest;

import com.example.beskbd.dto.request.OrderCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Order;
import com.example.beskbd.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestOrderController {
    static Logger logger = LoggerFactory.getLogger(RestOrderController.class);
    OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreationRequest request) {
        logger.info("Creating order with details: {}", request);
        orderService.addOrder(request);  // Adjust this according to your service method
        logger.info("Order created successfully");
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
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