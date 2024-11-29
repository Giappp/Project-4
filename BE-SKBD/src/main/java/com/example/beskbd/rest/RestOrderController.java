package com.example.beskbd.rest;

import com.example.beskbd.dto.request.CancelOrderRequest;
import com.example.beskbd.dto.request.CheckOrderRequest;
import com.example.beskbd.dto.request.CreateOrderRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestOrderController {
    OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        var order = orderService.createOrder(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(order)
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/status")
    public ResponseEntity<?> checkStatus(@RequestBody CheckOrderRequest request) {
        var order = orderService.checkOrder(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(order)
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody CancelOrderRequest request) {
        var result = orderService.cancelOrder(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(result)
                        .success(true)
                        .build()
        );
    }
}