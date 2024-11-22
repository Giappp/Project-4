package com.example.beskbd.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Setter
public class CategoryCreationRequest {
    @NotNull
    String categoryName;
    String categoryDescription;
    @NotNull
    String gender;
    @NotNull
    String productType;
}
