package com.example.beskbd.dto.response;

import com.example.beskbd.dto.object.CategoryDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryByGenderResponse {
    Integer id;
    String name;
    List<CategoryDto> categories;
}
