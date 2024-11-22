package com.example.beskbd.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ProductDto {
    Long productId;
    String productName;
    String productImageUrl;
    BigDecimal productMinPrice;
    BigDecimal productMaxPrice;
    List<String> productSizes;
    List<String> productColors;
    String productType;
}
