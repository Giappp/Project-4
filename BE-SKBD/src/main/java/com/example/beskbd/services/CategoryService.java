package com.example.beskbd.services;

import com.example.beskbd.dto.request.CategoryCreationRequest;

import com.example.beskbd.entities.Category;
import com.example.beskbd.repositories.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public void createNewCategory(CategoryCreationRequest request) {
        try {
            Category.Gender gender = Category.Gender.valueOf(request.getGender().toUpperCase());
            Category category = Category.builder()
                    .gender(gender)
                    .description(request.getCategoryDescription())
                    .name(request.getCategoryName())
                    .productType(request.getProductType())
                    .build();
            categoryRepository.save(category);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid gender value: " + request.getGender());
        }
    }


}
