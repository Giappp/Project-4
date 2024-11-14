package com.example.beskbd.dto.object;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductAttributeDto {
    private String color;
    private Integer stock;
    private Integer size;
    private BigDecimal price;
    private List<MultipartFile> imageFiles;
}
