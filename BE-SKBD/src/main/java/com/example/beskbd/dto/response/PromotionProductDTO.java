package com.example.beskbd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionProductDTO {
    private Long id;
    private Long productId; // Assuming you have a ProductDTO or similar
    private Float percentage;
}
