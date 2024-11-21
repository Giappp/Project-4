package com.example.beskbd.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryCreationRequest {
    @NotNull
    String categoryName;
    String categoryDescription;
    @NotNull
    String gender;
    @NotNull
    String productType;
}
