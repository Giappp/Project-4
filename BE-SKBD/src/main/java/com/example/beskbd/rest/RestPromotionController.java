package com.example.beskbd.rest;

import com.example.beskbd.dto.request.PromotionCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class RestPromotionController {

    private final PromotionService promotionService; // Use constructor-based injection

    @PostMapping("/create")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionCreationRequest request) {
        promotionService.createPromotion(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPromotions() {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(promotionService.getAllPromotions())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(promotionService.getPromotionById(id))
                .build());
    }
    @GetMapping("/products/{promotionId}")
    public ResponseEntity<?> getPromotionProducts(@PathVariable Long promotionId) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(promotionService.getPromotionProducts(promotionId))
                .build());
    }
}