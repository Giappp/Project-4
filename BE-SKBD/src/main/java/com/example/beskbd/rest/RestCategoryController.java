package com.example.beskbd.rest;

import com.example.beskbd.dto.request.CategoryCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestCategoryController {
    @Autowired
    public CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreationRequest request) {
       categoryService.createNewCategory(request);
       return ResponseEntity.ok(ApiResponse.builder()
               .success(true)
               .build());
   }
}
