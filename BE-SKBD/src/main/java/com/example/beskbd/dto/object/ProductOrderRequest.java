package com.example.beskbd.dto.object;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ProductOrderRequest {
    Long productAttributeId;
    String color;
    Integer quantity;
    Integer size;
    BigDecimal price;
}
