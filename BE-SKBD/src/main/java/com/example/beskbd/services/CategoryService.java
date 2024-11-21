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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public void createNewCategory(CategoryCreationRequest request) {
        Category category = Category.builder()
                .gender(Category.Gender.valueOf(request.getGender().toUpperCase()))
                .description(request.getCategoryDescription())
                .name(request.getCategoryName())
                .productType(request.getProductType())
                .build();
        categoryRepository.save(category);
    }
}
