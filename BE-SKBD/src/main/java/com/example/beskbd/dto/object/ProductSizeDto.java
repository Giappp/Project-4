package com.example.beskbd.dto.object;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizeDto {
    @NotNull
    @Min(1)
    private Integer stock;
    @NotNull
    @Min(0)
    private Integer size;
}
