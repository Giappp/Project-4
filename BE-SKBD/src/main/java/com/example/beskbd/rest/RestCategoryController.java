package com.example.beskbd.rest;

import com.example.beskbd.dto.request.CategoryCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class RestCategoryController {
            @Autowired
    public final CategoryService categoryService; // Use constructor-based injection


    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreationRequest request) {
        categoryService.createNewCategory(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(categoryService.getAllCategories())
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(categoryService.getCategoryById(id))
                .build());
    }
}
