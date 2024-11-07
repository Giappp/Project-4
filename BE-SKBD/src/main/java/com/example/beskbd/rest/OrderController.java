package com.example.beskbd.rest;

import com.example.beskbd.dto.request.OrderDTO;
import com.example.beskbd.dto.response.ApiResponse;

import com.example.beskbd.entities.Delivery;
import com.example.beskbd.entities.Oders.OrderEntity;
import com.example.beskbd.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDto) {
        logger.info("Creating order: {}", orderDto);
        OrderDTO createdOrder = orderService.createOrder(orderDto);
        return ApiResponse.<OrderDTO>builder()
                .data(createdOrder)
                .message("Order created successfully")
                .success(true)
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> getOrder(@PathVariable Long orderId) {
        logger.info("Retrieving order with ID: {}", orderId);
        OrderDTO order = orderService.getOrderById(orderId);
        return ApiResponse.<OrderDTO>builder()
                .data(order)
                .message("Order retrieved successfully")
                .success(true)
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getAllOrders() {
        logger.info("Retrieving all orders");
        List<OrderDTO> orders = orderService.getAllOrders();
        return ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .message("Orders retrieved successfully")
                .success(true)
                .build();
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam Delivery status) {
        logger.info("Updating order status for order ID: {} to {}", orderId, status);
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ApiResponse.<OrderDTO>builder()
                .data(updatedOrder)
                .message("Order status updated successfully")
                .success(true)
                .build();
    }
}