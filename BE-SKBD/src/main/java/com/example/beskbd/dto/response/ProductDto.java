package com.example.beskbd.dto.response;

import com.example.beskbd.entities.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long productId;
    String productName;
    String productImageUrl;
    BigDecimal productMinPrice;
    BigDecimal productMaxPrice;
    List<Integer> productSizes;
    List<String> productColors;
    String productType;

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
    }
}
