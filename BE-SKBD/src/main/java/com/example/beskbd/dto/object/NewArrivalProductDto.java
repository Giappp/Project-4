package com.example.beskbd.dto.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@Getter
@Setter
public class NewArrivalProductDto {
    public Long productId;
    public String productName;
    public  String categoryName;
    public String imageUrl;
    public BigDecimal minPrice;
    public BigDecimal maxPrice;
    String productDescription;
}
