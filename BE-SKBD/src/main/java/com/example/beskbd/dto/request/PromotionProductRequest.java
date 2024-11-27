package com.example.beskbd.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromotionProductRequest {
    @NotNull(message = "Product ID is required")
    private Long productId; // ID of the product

    @NotNull(message = "Percentage is required")
    private Float percentage; // Discount percentage
}
