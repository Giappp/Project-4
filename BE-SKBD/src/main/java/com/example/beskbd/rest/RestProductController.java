package com.example.beskbd.rest;

import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestProductController {
    static Logger logger = LoggerFactory.getLogger(RestProductController.class);
    ProductService productService;

    @GetMapping("/genders")
    public ResponseEntity<?> getByGender() {
        var result = productService.getCategoryByGender();
        return ResponseEntity.ok(ApiResponse.builder()
                .data(result)
                .success(true)
                .build());
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreationRequest request) {
        logger.info(request.toString());
        productService.addProduct(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<?> getNewArrivals() {
        var products = productService.getNewArrivalProduct();
        return ResponseEntity.ok(ApiResponse.builder()
                .data(products)
                .success(true)
                .build());
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        var products = productService;
        return ResponseEntity.ok(ApiResponse
                .builder()
                .success(true)
                .build());
    }
}