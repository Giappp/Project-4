package com.example.beskbd.dto.object;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilterDto {
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Long categoryId;
    List<String> productSizes;
    List<String> productColors;
    String productType;
}
