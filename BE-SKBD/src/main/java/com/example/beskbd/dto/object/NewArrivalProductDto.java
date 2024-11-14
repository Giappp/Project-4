package com.example.beskbd.dto.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewArrivalProductDto {
    private Long productId;
    private String productName;
    private String categoryName;
    private String imageUrl;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
