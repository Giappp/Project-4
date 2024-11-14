package com.example.beskbd.rest;

import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestProductController {
    static Logger logger = LoggerFactory.getLogger(RestProductController.class);
    ProductService productService;

    @GetMapping("/genders")
    public ApiResponse<?> getByGender() {
        var result = productService.getCategoryByGender();
        return ApiResponse.builder()
                .data(result)
                .success(true)
                .build();
    }

    @PostMapping("/")
    public ApiResponse<?> createProduct(@RequestBody @Valid ProductCreationRequest request) {
        productService.addProduct(request);
        return ApiResponse.builder()
                .success(true)
                .build();
    }

    @GetMapping("/new-arrivals")
    public ApiResponse<?> getNewArrivals() {
        var products = productService.getNewArrivalProduct();
        return ApiResponse.builder()
                .data(products)
                .success(true)
                .build();
    }

    @GetMapping("/")
    public ApiResponse<?> getAllProducts() {
        var products = productService;
        return ApiResponse
                .builder()
                .success(true)
                .build();
    }
}