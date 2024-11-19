package com.example.beskbd.dto.object;

import com.example.beskbd.entities.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    Long id;
    String categoryName;
    String gender;
    String productType;

    public CategoryDto(Category category) {
        if (category != null) {
            this.id = category.getId();
            this.categoryName = category.getName();
            this.productType = category.getProductType();
        }
    }
}
