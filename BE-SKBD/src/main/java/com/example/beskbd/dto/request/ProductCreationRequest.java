package com.example.beskbd.dto.request;

import com.example.beskbd.dto.object.ProductAttributeDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCreationRequest {
    @NotNull
    private String productName;
    @NotNull
    private String productDescription;
    private List<ProductAttributeDto> attributes;
    @NotNull
    private String categoryName;
    @NotNull
    private Long categoryId;
}
